import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { JobService } from "../_services/job.service";
import { TokenStorageService } from "../_services/token-storage.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-job-details",
  templateUrl: "./job-details.component.html",
  styleUrls: ["./job-details.component.css"],
})
export class JobDetailsComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private jobService: JobService,
    private tokenStorageService: TokenStorageService,
    private toastr: ToastrService,
    private router: Router
  ) {
    if (!this.tokenStorageService.isLoggedIn()) {
      this.router.navigateByUrl("/login");
      this.toastr.warning("You're not logged in !");
    }
  }

  jobDetail: any;
  role: string;

  ngOnInit() {
    this.role = this.tokenStorageService.getUser().roles[0];

    const jobId = this.route.snapshot.params.id;

    this.jobService.findOneById(jobId).subscribe(
      (data) => {
        this.jobDetail = data;
      },
      (err) => {
        this.showError(err.error.message);
      }
    );
  }

  showError(message: string) {
    this.toastr.error(message);
  }

  showSuccess(message: string) {
    this.toastr.success(message);
  }

  applyToJob(event, id) {
    console.log(id);
  }
}
