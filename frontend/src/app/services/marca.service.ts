import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Marca } from '../models/marca.model';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {

  private apiUrl = 'http://localhost:8080/marcas';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Marca[]> {
    return this.http.get<Marca[]>(this.apiUrl);
  }

  findById(id: number): Observable<Marca> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Marca>(url);
  }

  create(marca: Marca): Observable<Marca> {
    return this.http.post<Marca>(this.apiUrl, marca);
  }

  update(id: number, marca: Marca): Observable<Marca> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Marca>(url, marca);
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

}