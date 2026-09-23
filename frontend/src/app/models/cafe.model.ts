import { Marca } from './marca.model';
import { CategoriaDoCafe } from './categoria-do-cafe.model';
import { NivelDeTorra } from './nivel-de-torra.model';
import { Tratamento } from './tratamento.model';
import { NotaSensorial } from './nota-sensorial.model';

export class Cafe {

  id!: number;

  nome!: string;

  descricao!: string;

  marca!: Marca;

  categoriaDoCafe!: CategoriaDoCafe;

  nivelDeTorra!: NivelDeTorra;

  tratamento!: Tratamento;

  notasSensoriais!: NotaSensorial[];

  pontuacaoSCA!: number;

  preco!: number;

  peso!: number;

  estoque!: number;

}