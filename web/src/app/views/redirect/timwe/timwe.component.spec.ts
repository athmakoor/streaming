import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimweComponent } from './timwe.component';

describe('TimweComponent', () => {
  let component: TimweComponent;
  let fixture: ComponentFixture<TimweComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimweComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TimweComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
