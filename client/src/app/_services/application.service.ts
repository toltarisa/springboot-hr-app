import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenStorageService } from "../_services/token-storage.service";
import { environment } from "../../environments/environment.dev";

@Injectable({
  providedIn: "root",
})
export class ApplicationService {
  private BASE_API_URL = `${environment.apiUrl}/jobs`;
  private httpOptions = {
    headers: new HttpHeaders({
      Authorization: "Bearer " + this.tokenStorage.getToken(),
    }),
  };

  constructor(
    private tokenStorage: TokenStorageService,
    private httpClient: HttpClient
  ) {}

  createApplication(
    jobId: number,
    name: string,
    email: string,
    phone: string,
    address: string,
    thoughts: string,
    file: File
  ): Observable<any> {
    const formData = new FormData();

    const request = JSON.stringify({
      name,
      email,
      phone,
      address,
      thoughts,
    });

    formData.append("request", request);
    formData.append("file", file);

    return this.httpClient.post(
      this.BASE_API_URL + `/${jobId}/applications`,
      formData,
      this.httpOptions
    );
  }

  getAllApplicationsOfJob(jobId: number): Observable<any> {
    return this.httpClient.get(
      this.BASE_API_URL + `/${jobId}/applications`,
      this.httpOptions
    );
  }

  getApplicationById(jobId: number, applicationId: number) {
    return this.httpClient.get(
      this.BASE_API_URL + `/${jobId}/applications/${applicationId}`,
      this.httpOptions
    );
  }
}
