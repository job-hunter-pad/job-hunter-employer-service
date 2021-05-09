package jobhunter.employerservice.kafka.listener;

import jobhunter.employerservice.kafka.producer.JobOfferProducer;
import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobApplicationStatus;
import jobhunter.employerservice.service.JobOfferService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final JobOfferProducer jobOfferProducer;
    private final JobOfferService jobOfferService;

    public Consumer(JobOfferProducer jobOfferProducer, JobOfferService jobOfferService) {
        this.jobOfferProducer = jobOfferProducer;
        this.jobOfferService = jobOfferService;
    }

    @KafkaListener(topics = "job_application", groupId = "group_job_application", containerFactory = "kafkaListenerContainerFactory")
    public void consumeJobApplication(JobApplication jobApplication) {
        System.out.println("Consumed JSON Message: " + jobApplication);

        if (jobApplication.getStatus().equals(JobApplicationStatus.PENDING)) {
            this.jobOfferService.addJobApplication(jobApplication);
        } else if (jobApplication.getStatus().equals(JobApplicationStatus.COMPLETED)) {
            this.jobOfferService.completeApplication(jobApplication).ifPresent(jobOfferProducer::postJobOffer);
        }

        System.out.println("Job Application added");
    }

}
