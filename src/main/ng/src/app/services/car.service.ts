import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Car } from '../model/car.model';
import { HttpHeaders } from '@angular/common/http';
import { StorageService } from './storage.service';

const baseUrl = '/api/cars';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient, private storageService: StorageService) {
    
   }
  

  getAll(): Observable<Car[]> {
    return this.http.get<Car[]>(baseUrl,httpOptions);
  }

  get(id: any): Observable<Car> {
    return this.http.get(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }

}