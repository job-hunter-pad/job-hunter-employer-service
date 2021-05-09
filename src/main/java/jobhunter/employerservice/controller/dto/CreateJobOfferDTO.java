package jobhunter.employerservice.controller.dto;

import jobhunter.employerservice.model.JobOffer;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class CreateJobOfferDTO {
    String jobName;
    String jobDescription;
    String date;
    String employerId;
    String employerName;
    float hourSalaryAmount;
    List<String> skills;

    public JobOffer createJobOffer() {
        return new JobOffer(
                getJobName(),
                getJobDescription(),
                getDate(),
                getEmployerId(),
                getEmployerName(),
                getHourSalaryAmount(),
                skills);
    }
}
