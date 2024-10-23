package com.telemetrydataservice.process;

import com.telemetrydataservice.serdes.VehicleDeserializer;
import com.telemetrydataservice.serdes.VehicleSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;
import com.telemetrydataservice.dto.VehicleDTO;


import java.util.HashMap;
import java.util.Map;

@Service
@EnableKafka
@EnableKafkaStreams
public class TelemetryStreamProcessor {


    @Bean
    public KStream processStrem(StreamsBuilder streamsBuilder) throws Exception {
         //  StreamsBuilder builder = new StreamsBuilder();


        KStream<String,VehicleDTO> stream = streamsBuilder
                .stream("data-collection-telemetry" , Consumed.with(Serdes.String(), getSreamRequestSerde()));

        stream.filter((key, data) -> Integer.parseInt(data.getSpeed()) > 100 || data.getHarshBreaking().equalsIgnoreCase("yes") || data.getHarshAcceleration().equalsIgnoreCase("yes") || data.getEngineCheck().equalsIgnoreCase("yes") || data.getTyreCheck().equalsIgnoreCase("yes"))
                .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
       // stream.filter((key, data) -> data.getHarshBreaking().equalsIgnoreCase("yes"))
         //       .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //stream.filter((key, data) -> data.getHarshAcceleration().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //stream.filter((key, data) -> data.getEngineCheck().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //stream.filter((key, data) -> data.getTyreCheck().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));

        return stream;
    }

    private Serde<VehicleDTO> getSreamRequestSerde() {
        Map<String, Object> serdeProps = new HashMap<>();
        final Serializer<VehicleDTO> streamRequestSerializer = new VehicleSerializer();
        serdeProps.put("VehiclePOJOClass", VehicleDTO.class);
        streamRequestSerializer.configure(serdeProps, false);

        final Deserializer<VehicleDTO> streamRequestDeserializer = new VehicleDeserializer();
        serdeProps.put("VehiclePOJOClass", VehicleDTO.class);
        streamRequestDeserializer.configure(serdeProps, false);

        return Serdes.serdeFrom(streamRequestSerializer, streamRequestDeserializer);
    }

}
