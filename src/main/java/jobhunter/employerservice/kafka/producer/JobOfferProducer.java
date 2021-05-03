package jobhunter.employerservice.kafka.producer;

import jobhunter.employerservice.model.JobOffer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobOfferProducer {

    private static final String TOPIC = "jobs";

    private final KafkaTemplate<String, JobOffer> kafkaTemplate;

    public JobOfferProducer(KafkaTemplate<String, JobOffer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String postJobOffer(JobOffer jobOffer) {
        kafkaTemplate.send(TOPIC, jobOffer);
        return "Published successfully";
    }

}
