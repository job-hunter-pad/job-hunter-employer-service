package jobhunter.employerservice.kafka.listener;

import jobhunter.employerservice.kafka.producer.JobOfferProducer;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.model.payment.JobOfferPayment;
import jobhunter.employerservice.service.JobApplicationNotCompletedException;
import jobhunter.employerservice.service.JobOfferService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentConsumer {

    private final JobOfferService jobOfferService;
    private final JobOfferProducer jobOfferProducer;

    public PaymentConsumer(JobOfferService jobOfferService, JobOfferProducer jobOfferProducer) {
        this.jobOfferService = jobOfferService;
        this.jobOfferProducer = jobOfferProducer;
    }

    @KafkaListener(topics = "payment", groupId = "group_payment_employer", containerFactory = "jobOfferPaymentKafkaListenerContainerFactory")
    public void consumeJobOfferPayment(JobOfferPayment jobOfferPayment) {

        if (jobOfferPayment.getStatus().equals("succeeded")) {

            try {
                Optional<JobOffer> jobOfferOptional = jobOfferService.completeJob(jobOfferPayment.getJobId());

                if (jobOfferOptional.isPresent()) {
                    JobOffer jobOffer = jobOfferOptional.get();
                    jobOfferProducer.postJobOffer(jobOffer);
                }

            } catch (JobApplicationNotCompletedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Consumed JSON Message: " + jobOfferPayment);
    }

}
