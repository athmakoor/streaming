import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AuthCookieService } from './common/services/cookie/auth-cookie.service';
import { LocalStorageService } from './common/services/localStorage/localstorage.service';
import { ScriptService } from './common/services/page/script.service';
import { UrlParser } from './common/utils/url-parser';
import { AuthService } from './services/auth/auth.service';
import { CommonService } from './services/data/common.service';
import { ZoneUtils } from './common/utils/zoneUtils';
import { Config } from './common/constants/config';

declare var TPay: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'soccer mania';

  urlParser: UrlParser;
  paramData: any;

  constructor(private readonly route: ActivatedRoute, private readonly dataService: CommonService,
              private readonly localStorageService: LocalStorageService, private readonly router: Router,
              private readonly cookieService: AuthCookieService, private readonly authService: AuthService,
              private readonly scriptService: ScriptService, public translate: TranslateService) {
    translate.addLangs(['en', 'fr']);
    translate.setDefaultLang('en');

    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/en|fr/) ? browserLang : 'en');

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
