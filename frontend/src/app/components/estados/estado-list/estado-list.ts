import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { EstadoService } from '../../../services/estado.service';
import { Estado } from '../../../models/estado';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  imports: [
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    RouterLink
  ],
  selector: 'app-estado-list',
  styleUrl: './estado-list.css',
  templateUrl: './estado-list.html',
})
export class EstadoListComponent {

  displayedColumns: string[] = [
    'numero',
    'nome',
    'sigla',
    'regiao',
    'acao'
  ];

  dataSource = new MatTableDataSource<Estado>();

  constructor(private estadoService: EstadoService) {}

  ngOnInit() {
    this.estadoService.findAll().subscribe((estados: Estado[]) => {
      this.dataSource.data = estados;
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}