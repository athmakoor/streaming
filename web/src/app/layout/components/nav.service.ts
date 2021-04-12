import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NavService {
  public appDrawer: any;
  constructor(private router: Router) {
  }

  public toggleNav(): void {
    this.appDrawer.toggle();
  }
}
