import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { NavService } from '../components/nav.service';

interface ScrollPositionRestore {
  event: Event;
  positions: { [K: number]: number };
  trigger: 'imperative' | 'popstate';
  idToRestore: number;
}

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit, AfterViewInit {
  @ViewChild('contentArea') private contentArea: ElementRef<HTMLElement>;
  @ViewChild('drawer') appDrawer: ElementRef<HTMLElement>;
  provider: any;

  constructor(private readonly cookieService: AuthCookieService, private readonly router: Router, private navService: NavService) { }

  ngOnInit(): void {
    this.provider = this.cookieService.getProvider();
  }

  ngAfterViewInit(): void {
    this.navService.appDrawer = this.appDrawer;
  }
}
