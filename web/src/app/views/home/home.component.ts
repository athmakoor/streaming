import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { ZoneUtils } from 'src/app/common/utils/zoneUtils';
import { AuthService } from 'src/app/services/auth/auth.service';
import { GamesService } from 'src/app/services/data/games.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  provider: any;
  locale: string;
  mdn: string;
  selectedPack: any;

  constructor(private readonly route: ActivatedRoute, private readonly gamesService: GamesService,
              private readonly router: Router, private readonly authService: AuthService,
              private readonly localStorageService: LocalStorageService,
              private readonly cookieService: AuthCookieService) {

    this.mdn = this.cookieService.getMdn();
    this.locale = this.cookieService.getLocale();
    this.provider = this.cookieService.getProvider();
    this.selectedPack = this.localStorageService.getPackData(this.mdn);

    if (this.mdn !== '') {
      this.authService.validateMdn(this.cookieService.getMdn()).subscribe(
        data => {
          if (data.authenticated) {
            this.router.navigate(['/' + this.provider + '/' + this.locale + '/games'], {
            });
          } else {
            if (this.locale && this.provider) {
              this.router.navigate(['/' + this.provider + '/' + this.locale + '/subscribe'], {
              });
            }
          }
        }
      );
    } else if (this.locale && this.provider) {
      this.router.navigate(['/' + this.provider + '/' + this.locale + '/subscribe'], {
      });
    } else {
      // Default locale/url

    }
  }

  ngOnInit(): void {
  }

  setInitialData(): void {
    const zoneUtils = new ZoneUtils();
    const locale = zoneUtils.getCountryLocale() || Config.COUNTRY_ZONE.EG;
    this.cookieService.setLocale(locale);
    this.cookieService.setProvider(Config.PROVIDER_BY_LOCALE[locale]);

    return this.cookieService.getProvider();
  }
}
