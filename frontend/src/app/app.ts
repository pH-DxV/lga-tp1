import { EstadoListComponent } from './components/estados/estado-list/estado-list';

import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [EstadoListComponent],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
