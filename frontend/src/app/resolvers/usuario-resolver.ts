import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';

import { UsuarioService } from '../services/usuario.service';
import { Usuario } from '../models/usuario.model';

export const usuarioResolver: ResolveFn<Usuario> = (route, state) => {
  return inject(UsuarioService).findById(
    Number(route.paramMap.get('id'))
  );
};