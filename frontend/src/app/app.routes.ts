import { Routes } from '@angular/router';

import { EstadoListComponent } from './components/estados/estado-list/estado-list';
import { EstadoFormComponent } from './components/estados/estado-form/estado-form';
import { estadoResolver } from './resolvers/estado-resolver';

export const routes: Routes = [
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