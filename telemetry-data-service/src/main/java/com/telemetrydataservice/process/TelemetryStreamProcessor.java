package com.telemetrydataservice.process;

import com.telemetrydataservice.serdes.VehicleDeserializer;
import com.telemetrydataservice.serdes.VehicleSerializer;
import lombok.Value;
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


    @Value("${kafka.topic.data-structured-telemetry}")
    private String targetTopic;

    @Bean
    public KStream processStrem(StreamsBuilder streamsBuilder) throws Exception {

        VehicleDTO vehicleDTO = new VehicleDTO();

        KStream<String,VehicleDTO> stream = streamsBuilder
                .stream("data-collection-telemetry" , Consumed.with(Serdes.String(), getSreamRequestSerde()));

        //stream.filter((key, data) -> Integer.parseInt(data.getSpeed()) > 100 || data.getHarshBreaking().equalsIgnoreCase("yes") || data.getHarshAcceleration().equalsIgnoreCase("yes") || data.getEngineCheck().equalsIgnoreCase("yes") || data.getTyreCheck().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
       // stream.filter((key, data) -> data.getHarshBreaking().equalsIgnoreCase("yes"))
         //       .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //stream.filter((key, data) -> data.getHarshAcceleration().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //stream.filter((key, data) -> data.getEngineCheck().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //stream.filter((key, data) -> data.getTyreCheck().equalsIgnoreCase("yes"))
          //      .to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));
        //return stream;

        KStream<String, VehicleDTO> filteredStream = stream.filter((key, data) -> checkConditions(data));

        // Send the filtered stream to the target topic
        filteredStream.to("data-structured-telemetry", Produced.with(Serdes.String(), new JsonSerde<>(VehicleDTO.class)));

        return filteredStream;
    }


    private boolean checkConditions(VehicleDTO data) {
        if (Integer.parseInt(data.getSpeed()) > 100) {
            data.setAlertMessage("Over speeding");
            return true;
        } else if (data.getHarshBreaking().equalsIgnoreCase("yes")) {
            data.setAlertMessage("Harsh breaking");
            return true;
        } else if (data.getHarshAcceleration().equalsIgnoreCase("yes")) {
            data.setAlertMessage("Harsh acceleration");
            return true;
        } else if (data.getEngineCheck().equalsIgnoreCase("yes")) {
            data.setAlertMessage("Please check engine; it requires service");
            return true;
        } else if (data.getTyreCheck().equalsIgnoreCase("yes")) {
            data.setAlertMessage("Please check tyre");
            return true;
        }
        return false; // No condition met
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
