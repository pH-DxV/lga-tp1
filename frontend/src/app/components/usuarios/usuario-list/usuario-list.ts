import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/usuario.model';

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
  selector: 'app-usuario-list',
  styleUrl: './usuario-list.css',
  templateUrl: './usuario-list.html',
})
export class UsuarioListComponent {

  displayedColumns: string[] = [
    'numero',
    'nome',
    'login',
    'cpf',
    'perfil',
    'acao'
  ];

  dataSource = new MatTableDataSource<Usuario>();

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.usuarioService.findAll().subscribe((usuarios: Usuario[]) => {
      this.dataSource.data = usuarios;
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}