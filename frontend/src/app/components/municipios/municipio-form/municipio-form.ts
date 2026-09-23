import { Location } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule
} from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';

import { ActivatedRoute, Router } from '@angular/router';

import { MunicipioService } from '../../../services/municipio.service';
import { Municipio } from '../../../models/municipio.model';
import { Estado } from '../../../models/estado.model';
import { EstadoService } from '../../../services/estado.service';

@Component({
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatSelectModule
  ],
  selector: 'app-municipio-form',
  styleUrl: './municipio-form.css',
  templateUrl: './municipio-form.html',
})
export class MunicipioFormComponent implements OnInit {

  readonly form: FormGroup;

  estados: Estado[] = [];

  private readonly location = inject(Location);

  constructor(
    private fb: FormBuilder,
    private municipioService: MunicipioService,
    private estadoService: EstadoService,
    private activatedRoute: ActivatedRoute,
    private snack: MatSnackBar,
    private router: Router
  ) {
    this.form = this.fb.group({
      id: [null],
      nome: [''],
      idEstado: [null]
    });
  }

  ngOnInit(): void {

    this.estadoService.findAll().subscribe({
      next: (estados) => {
        this.estados = estados;
      },
      error: (error) => {
        console.error('Erro ao buscar estados:', error);
      }
    });

    const municipio = this.activatedRoute.snapshot.data['municipio'];

    if (municipio) {

      this.form.patchValue({
        id: municipio.id,
        nome: municipio.nome,
        idEstado: municipio.estado?.id
      });

    }

  }

  salvar(): void {

    const municipio = this.form.value;

    const resultado = municipio.id
      ? this.municipioService.update(municipio.id, municipio)
      : this.municipioService.create(municipio);

    resultado.subscribe({
      next: () => {
        this.exibirMensagem('Município salvo com sucesso!');
        this.router.navigate(['/municipios']);
      },
      error: (error) => {
        this.exibirMensagem('Erro ao salvar município!');
        console.error('Erro ao salvar município:', error);
      }
    });

  }

  excluir(): void {

    const municipio = this.form.value;

    if (municipio.id) {

      this.municipioService.delete(municipio.id).subscribe({
        next: () => {
          this.exibirMensagem('Município excluído com sucesso!');
          this.router.navigate(['/municipios']);
        },
        error: (error) => {
          this.exibirMensagem('Erro ao excluir município!');
          console.error('Erro ao excluir município:', error);
        }
      });

    }

  }

  exibirMensagem(mensagem: string): void {

    this.snack.open(mensagem, 'Ok', {
      duration: 2500,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });

  }

  voltar(): void {
    this.location.back();
  }

}