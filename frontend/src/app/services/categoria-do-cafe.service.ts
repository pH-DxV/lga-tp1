import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CategoriaDoCafe } from '../models/categoria-do-cafe.model';

@Injectable({
  providedIn: 'root'
})
export class CategoriaDoCafeService {

  private apiUrl = 'http://localhost:8080/categorias';

  constructor(private http: HttpClient) {}

  findAll(): Observable<CategoriaDoCafe[]> {
    return this.http.get<CategoriaDoCafe[]>(this.apiUrl);
  }

  findById(id: number): Observable<CategoriaDoCafe> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<CategoriaDoCafe>(url);
  }

  create(categoria: CategoriaDoCafe): Observable<CategoriaDoCafe> {
    return this.http.post<CategoriaDoCafe>(this.apiUrl, categoria);
  }

  update(
    id: number,
    categoria: CategoriaDoCafe
  ): Observable<CategoriaDoCafe> {

    const url = `${this.apiUrl}/${id}`;

    return this.http.put<CategoriaDoCafe>(
      url,
      categoria
    );
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

}