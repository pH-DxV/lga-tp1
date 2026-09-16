import { Routes } from '@angular/router';

import { EstadoListComponent } from './components/estados/estado-list/estado-list';
import { EstadoFormComponent } from './components/estados/estado-form/estado-form';
import { estadoResolver } from './resolvers/estado-resolver';

import { UsuarioListComponent } from './components/usuarios/usuario-list/usuario-list';
import { UsuarioFormComponent } from './components/usuarios/usuario-form/usuario-form';
import { usuarioResolver } from './resolvers/usuario-resolver';

export const routes: Routes = [

  // ROUTES DE USUARIOS
  {
    path: 'usuarios',
    component: UsuarioListComponent,
    title: 'Lista de Usuários'
  },
  {
    path: 'usuarios/new',
    component: UsuarioFormComponent,
    title: 'Novo Usuário'
  },
  {
    path: 'usuarios/edit/:id',
    component: UsuarioFormComponent,
    title: 'Editar Usuário',
    resolve: {
      usuario: usuarioResolver
    }
  },

  // ROUTES DE ESTADOS
  {
    path: 'estados',
    component: EstadoListComponent,
    title: 'Lista de Estados'
  },
  {
    path: 'estados/new',
    component: EstadoFormComponent,
    title: 'Novo Estado'
  },
  {
    path: 'estados/edit/:id',
    component: EstadoFormComponent,
    title: 'Editar Estado',
    resolve: {
      estado: estadoResolver
    }
  }
];