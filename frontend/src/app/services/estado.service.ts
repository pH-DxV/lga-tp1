import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Estado } from '../models/estado'

@Injectable({
  providedIn: 'root'
})
export class EstadoService {

  private apiUrl = 'http://localhost:8080/estados'

  constructor(private http: HttpClient) {}

  findAll(): Observable<Estado[]> {
    return this.http.get<Estado[]>(this.apiUrl)
  }
}