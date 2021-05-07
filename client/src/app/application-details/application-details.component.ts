import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ApplicationService } from "../_services/application.service";

@Component({
  selector: "app-application-details",
  templateUrl: "./application-details.component.html",
  styleUrls: ["./application-details.component.css"],
})
export class ApplicationDetailsComponent implements OnInit {
  applicationId;
  jobId;
  application;

  constructor(
    private route: ActivatedRoute,
    private applicationService: ApplicationService
  ) {}

  ngOnInit() {
    this.applicationId = this.route.snapshot.params.id;
    this.jobId = this.route.snapshot.params.jobId;

    this.applicationService
      .getApplicationById(this.jobId, this.applicationId)
      .subscribe(
        (data) => {
          this.application = data;
        },
        (err) => {
          console.log(err.error.message);
        }
      );
  }
}
