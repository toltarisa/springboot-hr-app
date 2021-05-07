import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { ApplicationService } from "../_services/application.service";
import { TokenStorageService } from "../_services/token-storage.service";

@Component({
  selector: "app-job-applications",
  templateUrl: "./job-applications.component.html",
  styleUrls: ["./job-applications.component.css"],
})
export class JobApplicationsComponent implements OnInit {
  applications: any;
  pageTitle;
  jobId;

  constructor(
    private route: ActivatedRoute,
    private application: ApplicationService,
    private toastr: ToastrService,
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) {}

  ngOnInit() {
    this.jobId = this.route.snapshot.params.id;
    this.pageTitle = "Applications";
    this.application.getAllApplicationsOfJob(this.jobId).subscribe(
      (data) => {
        console.log(data);
        this.applications = data;
      },
      (err) => {
        console.log(err.error.message);
      }
    );
  }

  logout() {
    this.tokenStorageService.signOut();
    this.router.navigateByUrl("/login");
    this.showSuccess("Logged Out Succesfully");
  }

  showSuccess(message: string) {
    this.toastr.success(message);
  }

  showError(error: string) {
    this.toastr.error(error);
  }
}
