import { Component, OnInit } from "@angular/core";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AuthService } from "../_services/auth.service";
import { ToastrService } from "ngx-toastr";
import { Router } from "@angular/router";
import { TokenStorageService } from "../_services/token-storage.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = "";
  isLoggedIn = false;
  isLoginFailed = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {
    this.loginForm = this.fb.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
    });
  }

  get username() {
    return this.loginForm.get("username");
  }

  get password() {
    return this.loginForm.get("password");
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }

  onSubmit() {
    const username = this.loginForm.get("username").value;
    const password = this.loginForm.get("password").value;

    this.authService.login(username, password).subscribe(
      (data) => {
        console.log(data);
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);

        this.showSuccess();
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.isSuccessful = true;
        this.isSignUpFailed = false;

        const role = data.roles[0];

        if (role === "ROLE_APPLICANT") this.router.navigateByUrl("/jobs");
        else if (role === "ROLE_MANAGER")
          this.router.navigateByUrl("/dashboard");
      },
      (err) => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        this.showError(err.error.message);
      }
    );
  }

  showSuccess() {
    this.toastr.success("Login Successful");
  }

  showError(message: string) {
    this.toastr.error(message);
  }
}
