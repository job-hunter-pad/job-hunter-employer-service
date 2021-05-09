package jobhunter.employerservice.kafka.config;

import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobOffer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Bean
    public ConsumerFactory<String, JobApplication> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_job_application");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<JobApplication> jobApplicationJsonDeserializer = new JsonDeserializer<>(JobApplication.class, false);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), jobApplicationJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobApplication> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JobApplication> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, JobOffer> jobOfferProducerFactory() {
        Map<String, Object> config = getProducerConfig();

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, JobOffer> kafkaTemplate() {
        return new KafkaTemplate<>(jobOfferProducerFactory());
    }

    @Bean
    public ProducerFactory<String, JobApplication> jobApplicationProducerFactory() {
        Map<String, Object> config = getProducerConfig();

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, JobApplication> jobApplicationsKafkaTemplate() {
        return new KafkaTemplate<>(jobApplicationProducerFactory());
    }

    private Map<String, Object> getProducerConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }
}
