package jobhunter.employerservice.controller.dto;

import jobhunter.employerservice.model.JobOffer;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class CreateJobOfferDTO {
    String jobName;
    String jobDescription;
    String date;
    String employerId;
    String employerName;
    float hourSalaryAmount;

    public JobOffer createJobOffer() {
        return new JobOffer(
                getJobName(),
                getJobDescription(),
                getDate(),
                getEmployerId(),
                getEmployerName(),
                getHourSalaryAmount());
    }
}
