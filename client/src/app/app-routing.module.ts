import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { HomeComponent } from "./home/home.component";
import { PublicJobsComponent } from "./public-jobs/public-jobs.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { JobDetailsComponent } from "./job-details/job-details.component";

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: "", component: HomeComponent },
  { path: "jobs", component: DashboardComponent },
  { path: "dashboard", component: DashboardComponent },
  { path: "jobs/:id/details", component: JobDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
