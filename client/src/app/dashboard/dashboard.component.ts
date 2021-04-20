import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { JobService } from "../_services/job.service";
import { TokenStorageService } from "../_services/token-storage.service";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.css"],
})
export class DashboardComponent implements OnInit {
  jobForm: FormGroup;
  modalState;
  jobs: any = [];
  role;

  constructor(
    private fb: FormBuilder,
    private jobService: JobService,
    private toastr: ToastrService,
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {
    this.jobForm = this.fb.group({
      title: ["", Validators.required],
      description: ["", Validators.required],
      people: ["", Validators.required],
      lastdate: [""],
    });
  }

  ngOnInit() {
    this.role = this.tokenStorageService.getUser().roles[0];
    this.getJobs();
  }

  get title() {
    return this.jobForm.get("title");
  }

  get description() {
    return this.jobForm.get("description");
  }

  get people() {
    return this.jobForm.get("people");
  }

  get lastdate() {
    return this.jobForm.get("lastdate");
  }

  public getJobs() {
    this.jobService.getAllJobs().subscribe(
      (data) => {
        this.jobs = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }

  onSubmit() {
    const title = this.jobForm.get("title").value;
    const description = this.jobForm.get("description").value;
    const people = this.jobForm.get("people").value;
    const lastdate = this.jobForm.get("lastdate").value;

    this.jobService.createJob(title, description, people, lastdate).subscribe(
      (data) => {
        this.showSuccess("Job Created Successfully");
        this.modalState = false;
        this.jobForm.reset();
        this.getJobs();
      },
      (err) => {
        if (err.error.length > 1) {
          err.error.forEach((element) => {
            this.showError(element.field + " " + element.message);
          });
        } else {
          this.showError(err.error.message);
        }
        this.showError(err.error.message);
      }
    );
  }

  showSuccess(message: string) {
    this.toastr.success(message);
  }

  showError(error: string) {
    this.toastr.error(error);
  }

  deleteJob(event, jobId) {
    this.jobService.deleteOneById(jobId).subscribe(
      (data) => {
        this.router.navigateByUrl("/dashboard");
        this.showSuccess("Job with id = " + jobId + " deleted successfully");
        this.getJobs();
      },
      (err) => {
        console.log(err.error);
        this.showError(err.error.message);
      }
    );
  }
}
