# HR Management

> Simple HR Management app built with Spring Boot & MySQL & Angular

## :scroll: Objectives

- Job Listing Management Module

  - Job listings should have attributes "Job Title", "Job Description", "Number of people to hire", "Last application date"
  - HR Manager should create new job listings.
  - HR Manager, should list the job listings on a listing page.
  - There should be show details link for each job listing on listing page.HR Manager, should see the details of a job listing by clicking the link.
  - There should be delete job listing link for each job listing on listing page. HR Manager, should delete a job listing by clicking the link.

- Add Authenticaton

  - Add an authentication mechanism and then only hr manager should manage job
    listings.
  - Login functionality for different roles
  - Signup to system

- Job Application Page

  - Create a listing page for the job listings that HR Manager created
  - Applicants can see the listings and click for the detail of a listing.
  - In job listing detail page, there should be a job application form.
  - Job application form should get the information "Name", "Email", "Phone", "Address", "Thoughts on Job", "Resume" (as a file) from the applicant and
    save these information to database.

- Application Listing Module

  - Create a job applications listing page. As an HR Manager, I should see all applications.
  - All job applications should have a detail page. As an HR Manager, I should see the detail page by clicking show detail link.
  - Only HR Manager should see the listing page. He/she should login to the system in order to see this page.

- CV Uploading

  - Upload CV file using AWS Java SDK

- UI
  - User friendly UI using Bootstrap.

## :wrench: Installation

1. Clone this repository to your local machine.
2. cd into client folder and execute `npm install`
3. modify application.properties file under `/src/main/resources`
4. Start both client and server apps

## 🔨 Tech Stack

- Java 11
- Spring Boot
- Lombok
- Spring Security
- JSON Web Token
- AWS Java SDK
- MySQL
- Angular 11
- Bootstrap

## Screenshots

<br/>

### Home Page

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/home.PNG" width="900" height="500">

### Register

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/register.PNG" width="900" height="500">

### Login

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/login.PNG" width="900" height="500">

### HR Manager Dashboard

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/hr_dashboard.PNG" width="900" height="500">

### HR Create Job Form

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/hr_create_job.PNG" width="900" height="500">

### HR Job Details

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/hr_job_detail.PNG" width="900" height="500">

### HR Job Applications

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/hr_job_application.PNG" width="900" height="500">

### HR Job Application Details

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/hr_job_application_detail.PNG" width="900" height="500">

### Applicant Dashboard

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/applicant_dashboard.PNG" width="900" height="500">

### Applicant Job Details

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/applicant_job_details.PNG" width="900" height="500">

### Applicant Apply Job

<img src="https://github.com/toltarisa/springboot-hr-app/blob/main/screenshots/applicant_job_application.PNG" width="900" height="500">
