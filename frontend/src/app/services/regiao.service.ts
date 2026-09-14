import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Regiao } from '../models/regiao.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegiaoService {

  private readonly apiUrl: string = 'http://localhost:8080/regioes';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Regiao[]> {
    return this.http.get<Regiao[]>(this.apiUrl);
  }

  findById(id: number): Observable<Regiao> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Regiao>(url);
  }
}