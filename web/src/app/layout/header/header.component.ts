import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { NavService } from '../components/nav.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  provider: any;
  mdn: any;
  locale: any;

  constructor(private readonly cookieService: AuthCookieService, private readonly router: Router, private navService: NavService) {
    this.mdn = this.cookieService.getMdn();
    this.provider = this.cookieService.getProvider();
    this.locale = this.cookieService.getLocale();
   }

  ngOnInit(): void {
  }

  onClick(): void {
    this.navService.toggleNav();
  }

  onHomeClick(): void {
    window.location.href = Config.APP_URL + '?msisdn=' + this.mdn + '&provider=' + Config.TIMWE;
  }
}
