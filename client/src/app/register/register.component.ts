import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { AuthService } from "../_services/auth.service";
@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.css"],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = "";
  roles: any = ["applicant", "manager"];

  temprole: string = "applicant";

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      name: ["", Validators.required],
      surname: ["", Validators.required],
      username: ["", Validators.required],
      email: ["", Validators.required],
      password: ["", Validators.required],
      role: ["", Validators.required],
    });
  }

  ngOnInit() {}

  get name() {
    return this.registerForm.get("name");
  }

  get surname() {
    return this.registerForm.get("surname");
  }

  get username() {
    return this.registerForm.get("username");
  }

  get email() {
    return this.registerForm.get("email");
  }

  get password() {
    return this.registerForm.get("password");
  }

  get role() {
    return this.registerForm.get("role");
  }

  changeCity(e) {
    this.role.setValue(e.target.value, {
      onlySelf: true,
    });
  }

  onSubmit() {
    console.log(this.registerForm.value);

    const name = this.registerForm.get("name").value;
    const username = this.registerForm.get("username").value;
    const password = this.registerForm.get("password").value;
    const surname = this.registerForm.get("surname").value;
    const email = this.registerForm.get("email").value;
    const role = this.registerForm.get("role").value.split(" ")[1].trim();

    this.authService
      .register(name, surname, username, email, password, role)
      .subscribe(
        (data) => {
          this.showSuccess(data.message);
          this.isSuccessful = true;
          this.isSignUpFailed = false;
          this.router.navigateByUrl("/login");
        },
        (err) => {
          console.log(err);
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
          if (err.error.length > 1) {
            err.error.forEach((element) => {
              this.showError(element.field + " " + element.message);
            });
          } else {
            this.showError(err.error.message);
          }
        }
      );
  }

  showSuccess(message: string) {
    this.toastr.success(message);
  }

  showError(message: string) {
    this.toastr.error(message);
  }
}
