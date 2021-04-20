import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenStorageService } from "./token-storage.service";

@Injectable({
  providedIn: "root",
})
export class JobService {
  private BASE_API_URL = "http://localhost:8080/api/jobs";
  private httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json",
      Authorization: "Bearer " + this.tokenStorage.getToken(),
    }),
  };

  constructor(
    private httpClient: HttpClient,
    private tokenStorage: TokenStorageService
  ) {}

  getAllJobs(): Observable<any> {
    return this.httpClient.get(this.BASE_API_URL, this.httpOptions);
  }

  createJob(
    title: string,
    description: string,
    numberOfPeople: number,
    lastApplicationDate: any
  ) {
    return this.httpClient.post(
      this.BASE_API_URL,
      {
        title,
        description,
        numberOfPeople,
        lastApplicationDate,
      },
      this.httpOptions
    );
  }

  findOneById(id: number) {
    return this.httpClient.get(this.BASE_API_URL + `/${id}`, this.httpOptions);
  }

  deleteOneById(id: number) {
    return this.httpClient.delete(
      this.BASE_API_URL + `/${id}`,
      this.httpOptions
    );
  }
}
