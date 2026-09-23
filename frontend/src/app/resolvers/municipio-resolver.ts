import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { MunicipioService } from '../services/municipio.service';
import { Municipio } from '../models/municipio.model';

export const municipioResolver: ResolveFn<Municipio> = (route, state) => {
  return inject(MunicipioService).findById(route.paramMap.get('id'));
};