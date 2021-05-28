import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnrichedComponent } from './enriched.component';

describe('EnrichedComponent', () => {
  let component: EnrichedComponent;
  let fixture: ComponentFixture<EnrichedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EnrichedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnrichedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
