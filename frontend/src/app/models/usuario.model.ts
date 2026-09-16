import { Perfil } from './perfil.model';
import { Telefone } from './telefone.model';
import { Endereco } from './endereco.model';

export class Usuario {
  id!: number;
  nome!: string;
  login!: string;
  cpf!: string;
  dataNascimento!: Date;
  perfis!: Perfil[];
  telefones!: Telefone[];
  enderecos!: Endereco[];
}