import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
@Injectable({
  providedIn: "root",
})
export class AuthService {
  private AUTH_API = "http://localhost:8080/api/auth/";
  private httpOptions = {
    headers: new HttpHeaders({ "Content-Type": "application/json" }),
  };

  constructor(private httpClient: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.httpClient.post(
      this.AUTH_API + "signin",
      {
        username,
        password,
      },
      this.httpOptions
    );
  }

  register(
    name: string,
    surname: string,
    username: string,
    email: string,
    password: string,
    role: any
  ): Observable<any> {
    return this.httpClient.post(
      this.AUTH_API + "register",
      {
        name,
        surname,
        username,
        email,
        password,
        role: [role],
      },
      this.httpOptions
    );
  }
}
