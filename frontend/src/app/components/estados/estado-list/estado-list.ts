import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { RouterLink } from '@angular/router';

import { EstadoService } from '../../../services/estado.service';
import { Estado } from '../../../models/estado.model';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  imports: [
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    RouterLink
  ],
  selector: 'app-estado-list',
  styleUrl: './estado-list.css',
  templateUrl: './estado-list.html',
})
export class EstadoListComponent implements AfterViewInit {

  displayedColumns: string[] = [
    'numero',
    'nome',
    'sigla',
    'regiao',
    'acao'
  ];

  dataSource = new MatTableDataSource<Estado>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  constructor(private estadoService: EstadoService) {}

  ngOnInit() {
    this.estadoService.findAll().subscribe((estados: Estado[]) => {
      this.dataSource.data = estados;
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  excluir(id: number): void {
    this.estadoService.delete(id).subscribe({
      next: () => {
        this.dataSource.data = this.dataSource.data.filter(
          estado => estado.id !== id
        );
      },
      error: (error) => {
        console.error('Erro ao excluir estado:', error);
      }
    });
  }

}