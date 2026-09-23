import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { RouterLink } from '@angular/router';

import { CafeService } from '../../../services/cafe.service';
import { Cafe } from '../../../models/cafe.model';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-cafe-list',
  templateUrl: './cafe-list.html',
  styleUrl: './cafe-list.css',
  imports: [
    MatTableModule,
    MatInputModule,
    MatFormFieldModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    RouterLink
  ]
})
export class CafeListComponent implements AfterViewInit {

  displayedColumns: string[] = [
    'numero',
    'nome',
    'marca',
    'categoria',
    'preco',
    'estoque',
    'acao'
  ];

  dataSource = new MatTableDataSource<Cafe>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  constructor(private cafeService: CafeService) {}

  ngOnInit() {
    this.cafeService.findAll().subscribe((cafes: Cafe[]) => {
      this.dataSource.data = cafes;
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue =
      (event.target as HTMLInputElement).value;

    this.dataSource.filter =
      filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  excluir(id: number): void {
    this.cafeService.delete(id).subscribe({
      next: () => {
        this.dataSource.data =
          this.dataSource.data.filter(cafe => cafe.id !== id);
      },
      error: (error) => {
        console.error('Erro ao excluir café:', error);
      }
    });
  }
}