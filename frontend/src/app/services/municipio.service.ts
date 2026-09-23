import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Municipio } from '../models/municipio.model';

@Injectable({
  providedIn: 'root'
})
export class MunicipioService {

  private apiUrl = 'http://localhost:8080/municipios';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Municipio[]> {
    return this.http.get<Municipio[]>(this.apiUrl);
  }

  findById(id: any): Observable<Municipio> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Municipio>(url);
  }

  create(municipio: Municipio): Observable<Municipio> {
    return this.http.post<Municipio>(this.apiUrl, municipio);
  }

  update(id: number, municipio: Municipio): Observable<Municipio> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Municipio>(url, municipio);
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

}