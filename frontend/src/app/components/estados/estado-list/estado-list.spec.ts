import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoList } from './estado-list';

describe('EstadoList', () => {
  let component: EstadoList;
  let fixture: ComponentFixture<EstadoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
