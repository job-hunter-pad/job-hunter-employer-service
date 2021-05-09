# Job Hunter Employer Service

## Mappings

| Service URL | API Gateway URL | Method | Description |
| ------ | ------ | ------ | ------ |
| / | /api/jobs | GET | Get All Job Offers |
| /{jobId} | /api/jobs/{jobId} | GET | Get the Job Offer by Id |
| /getJobOffers/{employerId} | /api/jobs/getJobOffers/{employerId} | GET | Get all Job Offers made by the Employer with the id of "employerId" |
| /getNotCompletedJobOffers/{employerId} | /api/jobs/getNotCompletedJobOffers/{employerId} | GET | Get all Job Offers with status not COMPLETED made by the Employer with the id of "employerId" |
| /getCompletedJobOffers/{employerId} | /api/jobs/getCompletedJobOffers/{employerId} | GET | Get all Job Offers with status COMPLETED made by the Employer with the id of "employerId" |
| /create | /api/jobs/create | POST | Create a new Job Offer |
| /update | /api/jobs/update | POST | Update a Job Offer |
| /getJobApplications/{jobId} | /api/jobs/getJobApplications/{jobId} | GET | Get All the Job Applications of the Job Offer with the id of "jobId" |
| /acceptApplication/{jobId}/{applicationId} | /api/jobs/acceptApplication/{jobId}/{applicationId} | POST | Application Status Will be changed to "ACCEPTED" for the Application with the id "applicationId" for the Job Offer with the id "jobId" |
| /rejectApplication/{jobId}/{applicationId} | /api/jobs/rejectApplication/{jobId}/{applicationId} | POST | Application Status Will be changed to "REJECTED" for the Application with the id "applicationId" for the Job Offer with the id "jobId" |

## Mappings Request And Responses

### Responses JSON

#### Job Offer

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": 0.0,
  "status": "",
  "applications": []
}
```

```java
public enum JobOfferStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}
```

#### Job Offer Application

```json
{
  "id": "",
  "jobId": "",
  "freelancerId": "",
  "freelancerName": "",
  "hourSalaryAmount": 0.0,
  "estimatedProjectCompleteTime": 0,
  "message": "",
  "status": ""
}
```

```java

public enum JobApplicationStatus {
    ACCEPTED,
    COMPLETED,
    PENDING,
    REJECTED
}

```

### Request JSON

#### Create Job Offer

```json
{
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": 0.0
}
```

#### Update Job Offer

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "hourSalaryAmount": 0.0
}
```


