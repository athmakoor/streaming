import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { ScriptService } from 'src/app/common/services/page/script.service';
import { ZoneUtils } from 'src/app/common/utils/zoneUtils';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommonService } from 'src/app/services/data/common.service';

@Component({
  selector: 'app-tpay',
  templateUrl: './tpay.component.html',
  styleUrls: ['./tpay.component.css']
})
export class TpayComponent implements OnInit {
  paramData: any;

  constructor(private readonly route: ActivatedRoute,
              private readonly cookieService: AuthCookieService) {

    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;
      this.setInitialData(this.paramData);
    });
  }

  ngOnInit(): void {
  }

  setInitialData(paramData: any): void {
    const zoneUtils = new ZoneUtils();
    const locale = zoneUtils.getCountryLocale() || paramData.locale || Config.COUNTRY_ZONE.EG;
    this.cookieService.setLocale(locale);
    this.cookieService.setProvider(Config.PROVIDER_BY_LOCALE[locale]);
  }
}
