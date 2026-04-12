import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // 👈 ADD AQUI
import { EstadoService } from '../../../services/estado.service';
import { Estado } from '../../../models/estado';

@Component({
  selector: 'app-estado-list',
  standalone: true,
  imports: [CommonModule], // 👈 E AQUI
  templateUrl: './estado-list.html',
  styleUrls: ['./estado-list.css'],
})
export class EstadoListComponent implements OnInit {

  estados: Estado[] = [];

  constructor(private estadoService: EstadoService) {}

  ngOnInit(): void {
    this.estadoService.findAll().subscribe(data => {
      this.estados = data;
    });
  }
}