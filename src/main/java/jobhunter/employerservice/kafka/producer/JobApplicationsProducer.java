package jobhunter.employerservice.kafka.producer;

import jobhunter.employerservice.model.JobApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationsProducer {

    private final KafkaTemplate<String, JobApplication> kafkaTemplate;

    private static final String TOPIC = "job_application";

    public JobApplicationsProducer(KafkaTemplate<String, JobApplication> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String postJobApplication(JobApplication jobApplication) {
        kafkaTemplate.send(TOPIC, jobApplication);
        return "Published successfully";
    }
}
