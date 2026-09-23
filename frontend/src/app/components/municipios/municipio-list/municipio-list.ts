import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MunicipioService } from '../../../services/municipio.service';
import { Municipio } from '../../../models/municipio.model';

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
  selector: 'app-municipio-list',
  styleUrl: './municipio-list.css',
  templateUrl: './municipio-list.html',
})
export class MunicipioListComponent implements AfterViewInit {

  displayedColumns: string[] = [
    'numero',
    'nome',
    'estado',
    'acao'
  ];

  dataSource = new MatTableDataSource<Municipio>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  constructor(private municipioService: MunicipioService) {}

  ngOnInit() {
    this.municipioService.findAll().subscribe((municipios: Municipio[]) => {
      this.dataSource.data = municipios;
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
    this.municipioService.delete(id).subscribe({
      next: () => {
        this.dataSource.data = this.dataSource.data.filter(
          municipio => municipio.id !== id
        );
      },
      error: (error) => {
        console.error('Erro ao excluir município:', error);
      }
    });
  }

}