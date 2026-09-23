import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Cafe } from '../models/cafe.model';

@Injectable({
  providedIn: 'root'
})
export class CafeService {

  private apiUrl = 'http://localhost:8080/cafes';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Cafe[]> {
    return this.http.get<Cafe[]>(this.apiUrl);
  }

  findById(id: number): Observable<Cafe> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Cafe>(url);
  }

  create(cafe: Cafe): Observable<Cafe> {
    return this.http.post<Cafe>(this.apiUrl, cafe);
  }

  update(id: number, cafe: Cafe): Observable<Cafe> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Cafe>(url, cafe);
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

}