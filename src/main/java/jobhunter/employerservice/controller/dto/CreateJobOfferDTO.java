package jobhunter.employerservice.controller.dto;

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
}
