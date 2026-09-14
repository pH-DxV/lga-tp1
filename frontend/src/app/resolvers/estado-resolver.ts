import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { EstadoService } from '../services/estado.service';
import { Estado } from '../models/estado';

export const estadoResolver: ResolveFn<Estado> = (route, state) => {
  return inject(EstadoService).findById(route.paramMap.get('id'));
};