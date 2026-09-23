import { Routes } from '@angular/router';

import { EstadoListComponent } from './components/estados/estado-list/estado-list';
import { EstadoFormComponent } from './components/estados/estado-form/estado-form';
import { estadoResolver } from './resolvers/estado-resolver';

import { UsuarioListComponent } from './components/usuarios/usuario-list/usuario-list';
import { UsuarioFormComponent } from './components/usuarios/usuario-form/usuario-form';
import { usuarioResolver } from './resolvers/usuario-resolver';

import { MunicipioListComponent } from './components/municipios/municipio-list/municipio-list';
import { MunicipioFormComponent } from './components/municipios/municipio-form/municipio-form';
import { municipioResolver } from './resolvers/municipio-resolver';

import { CafeListComponent } from './components/cafes/cafe-list/cafe-list';
import { CafeFormComponent } from './components/cafes/cafe-form/cafe-form';
import { cafeResolver } from './resolvers/cafe-resolver';

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
  },

  // ROUTES DE MUNICIPIOS
  {
    path: 'municipios',
    component: MunicipioListComponent,
    title: 'Lista de Municípios'
  },
  {
    path: 'municipios/new',
    component: MunicipioFormComponent,
    title: 'Novo Município'
  },
  {
    path: 'municipios/edit/:id',
    component: MunicipioFormComponent,
    title: 'Editar Município',
    resolve: {
      municipio: municipioResolver
    }
  },

  // ROUTES DE CAFE
  {
    path: 'cafes',
    component: CafeListComponent,
    title: 'Lista de Cafés'
  },
  {
    path: 'cafes/new',
    component: CafeFormComponent,
    title: 'Novo Café'
  },
  {
    path: 'cafes/edit/:id',
    component: CafeFormComponent,
    title: 'Editar Café',
    resolve: {
      cafe: cafeResolver
    }
  },


];