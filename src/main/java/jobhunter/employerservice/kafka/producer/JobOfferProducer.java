package jobhunter.employerservice.kafka.producer;

import jobhunter.employerservice.controller.dto.CreateJobOfferDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobOfferProducer {

    private static final String TOPIC = "jobs";

    private final KafkaTemplate<String, CreateJobOfferDTO> kafkaTemplate;

    public JobOfferProducer(KafkaTemplate<String, CreateJobOfferDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String postJobOffer(CreateJobOfferDTO jobOfferDTO) {
        kafkaTemplate.send(TOPIC, jobOfferDTO);
        return "Published successfully";
    }

}
