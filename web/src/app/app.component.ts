import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from './common/constants/config';
import { AuthCookieService } from './common/services/cookie/auth-cookie.service';
import { LocalStorageService } from './common/services/localStorage/localstorage.service';
import { ScriptService } from './common/services/page/script.service';
import { UrlParser } from './common/utils/url-parser';
import { ZoneUtils } from './common/utils/zoneUtils';
import { AuthService } from './services/auth/auth.service';
import { CommonService } from './services/data/common.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'SOCCERMANIA';

  urlParser: UrlParser;
  paramData: any;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router,
              private readonly cookieService: AuthCookieService, private readonly authService: AuthService,
              private readonly scriptService: ScriptService) {

    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;
      this.setInitialData(this.paramData);
    });
  }

  setInitialData(paramData: any): void {
    const zoneUtils = new ZoneUtils();
    const locale = zoneUtils.getCountryLocale() || paramData.locale;

    if (locale) {
      this.cookieService.setLocale(locale);
      this.cookieService.setProvider(Config.PROVIDER_BY_LOCALE[locale]);
    }
  }
}
