package jobhunter.employerservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class JobOffer {
    @Id
    private String id;

    private String jobName;
    private String jobDescription;
    private String date;
    private String employerId;
    private String employerName;
    private float hourSalaryAmount;
    private JobOfferStatus status;
    private List<JobApplication> applications;

    public JobOffer(String jobName, String jobDescription, String date, String employerId, String employerName, float hourSalaryAmount) {
        this.jobName = jobName;
        this.jobDescription = jobDescription;
        this.date = date;
        this.employerId = employerId;
        this.employerName = employerName;
        this.hourSalaryAmount = hourSalaryAmount;
        status = JobOfferStatus.PENDING;
        applications = new ArrayList<>();
    }
}
