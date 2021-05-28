import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TpayComponent } from './tpay.component';

describe('TpayComponent', () => {
  let component: TpayComponent;
  let fixture: ComponentFixture<TpayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TpayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TpayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
