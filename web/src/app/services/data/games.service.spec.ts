import { TestBed } from '@angular/core/testing';

import { GamezService } from './games.service';

describe('GamezService', () => {
  let service: GamezService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GamezService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
