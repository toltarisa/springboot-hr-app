import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { JobService } from "../_services/job.service";
import { TokenStorageService } from "../_services/token-storage.service";
import { ToastrService } from "ngx-toastr";
import { ApplicationService } from "../_services/application.service";
import { FormBuilder, FormGroup } from "@angular/forms";

@Component({
  selector: "app-job-details",
  templateUrl: "./job-details.component.html",
  styleUrls: ["./job-details.component.css"],
})
export class JobDetailsComponent implements OnInit {
  jobDetail: any;
  role: string;
  applicationForm: FormGroup;
  file: File;

  constructor(
    private route: ActivatedRoute,
    private jobService: JobService,
    private tokenStorageService: TokenStorageService,
    private toastr: ToastrService,
    private router: Router,
    private applicationService: ApplicationService,
    private fb: FormBuilder
  ) {
    if (!this.tokenStorageService.isLoggedIn()) {
      this.router.navigateByUrl("/login");
      this.toastr.warning("You're not logged in !");
    }

    this.applicationForm = this.fb.group({
      name: [""],
      email: [""],
      phone: [""],
      address: [""],
      thoughts: [""],
    });
  }

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
    event.preventDefault();

    const name = this.applicationForm.get("name").value;
    const email = this.applicationForm.get("email").value;
    const phone = this.applicationForm.get("phone").value;
    const address = this.applicationForm.get("address").value;
    const thoughts = this.applicationForm.get("thoughts").value;
    this.applicationService
      .createApplication(id, name, email, phone, address, thoughts, this.file)
      .subscribe(
        (data) => {
          this.showSuccess(data.message);
          this.applicationForm.reset();
        },
        (err) => {
          this.showError(err.error.message);
        }
      );
  }

  onFileChange(event) {
    this.file = event.target.files[0] as File;
  }
}
