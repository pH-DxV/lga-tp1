import { Municipio } from './municipio.model';

export class Endereco {
  id!: number;
  cep!: string;
  rua!: string;
  numero!: string;
  complemento!: string;
  bairro!: string;
  municipio!: Municipio;
}