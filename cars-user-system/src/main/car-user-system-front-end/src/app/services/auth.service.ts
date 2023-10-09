import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const api = '/api/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(login: string, password: string): Observable<any> {
    return this.http.post(
      api + 'signin',
      {
        login,
        password,
      },
      httpOptions
    );
  }

  me(): Observable<any> {
    return this.http.post(api+"me",httpOptions);
  }
}