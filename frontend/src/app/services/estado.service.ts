import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Estado } from '../models/estado.model'

@Injectable({
  providedIn: 'root'
})
export class EstadoService {

  private apiUrl = 'http://localhost:8080/estados' // <-- verificar a nomenclatura

  constructor(private http: HttpClient) {}

  findAll(): Observable<Estado[]> {
    return this.http.get<Estado[]>(this.apiUrl)
  }

  findById(id: any): Observable<Estado> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Estado>(url); 
  }

  create(estado: Estado): Observable<Estado> {
    return this.http.post<Estado>(this.apiUrl, estado);
  }

  update(id: number, estado: Estado): Observable<Estado> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Estado>(url, estado);
  }

  delete(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

}