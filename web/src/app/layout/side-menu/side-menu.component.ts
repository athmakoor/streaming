import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'protractor';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { GamesService } from 'src/app/services/data/games.service';
import { NavService } from '../components/nav.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit {
  mdn: any;
  provider: any;
  hasSubscription: boolean;
  link: any;
  constructor( private navService: NavService, private readonly router: Router,
               private readonly route: ActivatedRoute,
               private readonly cookieService: AuthCookieService) {
    this.route.queryParams.subscribe((params: any) => {
      this.mdn = params.msisdn || this.cookieService.getMdn();
      this.provider = this.cookieService.getProvider();
      this.link = '/unsubscribe';


      if (this.provider) {
        this.hasSubscription = true;
        this.link = this.provider + '/unsubscribe';
      }
    });
  }

  ngOnInit(): void {
  }

  unsub(): void {
    this.provider = this.cookieService.getProvider();
    this.link = '/unsubscribe';

    if (this.provider) {
      this.hasSubscription = true;
      this.link = this.provider + '/unsubscribe';
      this.toggleNav();
      this.router.navigate(['/' +  this.link]);
    }
  }

  toggleNav(): void {
    this.navService.toggleNav();
 }
}
