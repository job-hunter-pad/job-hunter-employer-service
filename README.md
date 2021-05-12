# Job Hunter Employer Service

### Get All Job Offers

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| / | /api/jobs | GET |

#### Response

List of JobOffers

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Get Job Offer by ID

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /{jobId} | /api/jobs/{jobId}  | GET |

#### Request

PathVariable: jobId

#### Response

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Get Job Offers of Employer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /getJobOffers/{employerId} | /api/jobs/getJobOffers/{employerId} | GET |

#### Description

Get all Job Offers made by the Employer with the id of `employerId`

#### Request

PathVariable: employerId

#### Response

List of JobOffers

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Get not COMPLETED Job Offers of Employer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /getNotCompletedJobOffers/{employerId} | /api/jobs/getNotCompletedJobOffers/{employerId} | GET |

#### Description

Get all Job Offers made by the Employer with the id of `employerId` that have the status different from `COMPLETED`

#### Request

PathVariable: employerId

#### Response

List of JobOffers

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Get COMPLETED Job Offers of Employer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /getCompletedJobOffers/{employerId} | /api/jobs/getCompletedJobOffers/{employerId} | GET | 

#### Description

Get all Job Offers with status `COMPLETED` made by the Employer with the id of `employerId`

#### Request

PathVariable: employerId

#### Response

List of JobOffers

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Create a new Job Offer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /create | /api/jobs/create | POST |  

#### Request

RequestParam: CreateJobOfferDTO

CreateJobOfferDTO

```json
{
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": "",
  "skills": []
}
```

#### Response

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Update Job Offer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /update | /api/jobs/update | POST |

#### Request

RequestParam: UpdateJobOfferDTO

UpdateJobOfferDTO

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "hourSalaryAmount": "",
  "skills": []
}
```

#### Response

Job Offer

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
  "skills": [],
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

Job Offer Application

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

### Get Job Applications

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /getJobApplications/{jobId} | /api/jobs/getJobApplications/{jobId} | GET |

#### Description

Get All the Job Applications of the Job Offer with the id of `jobId`

#### Request

PathVariable: jobId

#### Response

List of JobApplications

Job Offer Application

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

### Accept Application

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /acceptApplication/{jobId}/{applicationId} | /api/jobs/acceptApplication/{jobId}/{applicationId} | POST |

#### Description

Application Status Will be changed to `ACCEPTED` for the Application with the id `applicationId` for the Job Offer with
the id `jobId`

#### Request

PathVariable: jobId

PathVariable: applicationId

#### Response

Job Offer Application

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

### Reject Application

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /rejectApplication/{jobId}/{applicationId} | /api/jobs/rejectApplication/{jobId}/{applicationId} | POST | 

#### Description

Application Status Will be changed to `REJECTED` for the Application with the id `applicationId` for the Job Offer with
the id `jobId`

#### Request

PathVariable: jobId

PathVariable: applicationId

#### Response

Job Offer Application

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

### Complete A Job Offer

| URL | API Gateway URL | Method |
| ------ | ------ | ------ |
| /completeJob/{jobId} | /api/jobs/completeJob/{jobId} | POST |

#### Description

Used to mark a job as completed. This will result in changing the status of the Job Offer with the id of `jobId`
to `COMPLETED`.
> Normally, the job completion is done automatically when the payment is completed

#### Request

PathVariable: jobId

#### Response

Job Offer

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
  "skills": [],
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

Job Offer Application

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