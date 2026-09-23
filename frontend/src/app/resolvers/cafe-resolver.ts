import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';

import { CafeService } from '../services/cafe.service';
import { Cafe } from '../models/cafe.model';

export const cafeResolver: ResolveFn<Cafe> = (route, state) => {
  return inject(CafeService).findById(
    Number(route.paramMap.get('id'))
  );
};