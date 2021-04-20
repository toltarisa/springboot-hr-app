import { Component, OnInit } from "@angular/core";
import { JobService } from "../_services/job.service";

@Component({
  selector: "app-public-jobs",
  templateUrl: "./public-jobs.component.html",
  styleUrls: ["./public-jobs.component.css"],
})
export class PublicJobsComponent implements OnInit {
  jobs: any = [];

  constructor(private jobService: JobService) {}

  ngOnInit() {
    this.jobService.getAllJobs().subscribe(
      (data) => {
        this.jobs = data;
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
