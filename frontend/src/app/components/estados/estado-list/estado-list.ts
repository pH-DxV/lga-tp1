import { Component, OnInit } from '@angular/core';
import { EstadoService } from '../../../services/estado.service'
import { Estado } from '../../../models/estado'

@Component({
  selector: 'app-estado-list',
  standalone: true,
  imports: [],
  templateUrl: './estado-list.html',
  styleUrls: ['./estado-list.css'],
})
export class EstadoListComponent implements OnInit{

    // 👉 1. AQUI ficam as variáveis da classe
  estados: Estado[] = []

  // 👉 2. AQUI fica o constructor
  constructor(private estadoService: EstadoService) {}

  // 👉 3. AQUI fica o ngOnInit
  ngOnInit(): void {
    this.estadoService.findAll().subscribe(data => {
      this.estados = data
    })
  }

}
