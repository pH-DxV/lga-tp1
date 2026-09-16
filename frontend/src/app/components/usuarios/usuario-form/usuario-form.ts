import { Location } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule
} from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';

import { ActivatedRoute, Router } from '@angular/router';

import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/usuario.model';
import { Municipio } from '../../../models/municipio.model';

@Component({
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatSnackBarModule,
    MatSelectModule
  ],
  selector: 'app-usuario-form',
  styleUrl: './usuario-form.css',
  templateUrl: './usuario-form.html',
})
export class UsuarioFormComponent implements OnInit {

  readonly form: FormGroup;

  municipios: Municipio[] = [];

  private readonly location = inject(Location);

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private activatedRoute: ActivatedRoute,
    private snack: MatSnackBar,
    private router: Router
  ) {
    this.form = this.fb.group({
      id: [null],
      nome: [''],
      login: [''],
      senha: [''],
      cpf: [''],
      idPerfil: [null],
      dataNascimento: [null],
      telefones: this.fb.array([]),
      enderecos: this.fb.array([])
    });
  }

  ngOnInit(): void {

    const usuario = this.activatedRoute.snapshot.data['usuario'];

    if (usuario) {

      this.form.patchValue({
        id: usuario.id,
        nome: usuario.nome,
        login: usuario.login,
        cpf: usuario.cpf,
        dataNascimento: usuario.dataNascimento
      });

      if (usuario.perfis?.length) {
        this.form.patchValue({
          idPerfil: usuario.perfis[0].id
        });
      }

      usuario.telefones?.forEach((telefone: any) => {
        this.adicionarTelefone(telefone);
      });

      usuario.enderecos?.forEach((endereco: any) => {
        this.adicionarEndereco({
          cep: endereco.cep,
          rua: endereco.rua,
          numero: endereco.numero,
          complemento: endereco.complemento,
          bairro: endereco.bairro,
          idMunicipio: endereco.municipio?.id
        });
      });

    } else {
      this.adicionarTelefone();
      this.adicionarEndereco();
    }

  }

  get telefones(): FormArray {
    return this.form.get('telefones') as FormArray;
  }

  get enderecos(): FormArray {
    return this.form.get('enderecos') as FormArray;
  }

  adicionarTelefone(telefone?: any): void {

    this.telefones.push(
      this.fb.group({
        ddd: [telefone?.ddd ?? ''],
        numero: [telefone?.numero ?? '']
      })
    );

  }

  removerTelefone(index: number): void {
    this.telefones.removeAt(index);
  }

  adicionarEndereco(endereco?: any): void {

    this.enderecos.push(
      this.fb.group({
        cep: [endereco?.cep ?? ''],
        rua: [endereco?.rua ?? ''],
        numero: [endereco?.numero ?? ''],
        complemento: [endereco?.complemento ?? ''],
        bairro: [endereco?.bairro ?? ''],
        idMunicipio: [endereco?.idMunicipio ?? null]
      })
    );

  }

  removerEndereco(index: number): void {
    this.enderecos.removeAt(index);
  }

  salvar(): void {

    const usuario = this.form.value;

    const resultado = usuario.id
      ? this.usuarioService.update(usuario.id, usuario)
      : this.usuarioService.create(usuario);

    resultado.subscribe({
      next: () => {
        this.exibirMensagem('Usuário salvo com sucesso!');
        this.router.navigate(['/usuarios']);
      },
      error: (error) => {
        this.exibirMensagem('Erro ao salvar usuário!');
        console.error('Erro ao salvar usuário:', error);
      }
    });

  }

  excluir(): void {

    const usuario = this.form.value;

    if (usuario.id) {

      this.usuarioService.delete(usuario.id).subscribe({
        next: () => {
          this.exibirMensagem('Usuário excluído com sucesso!');
          this.router.navigate(['/usuarios']);
        },
        error: (error) => {
          this.exibirMensagem('Erro ao excluir usuário!');
          console.error('Erro ao excluir usuário:', error);
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