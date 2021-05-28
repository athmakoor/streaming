import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { ZoneUtils } from 'src/app/common/utils/zoneUtils';

@Component({
  selector: 'app-timwe',
  templateUrl: './timwe.component.html',
  styleUrls: ['./timwe.component.css']
})
export class TimweComponent implements OnInit {
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

    return this.cookieService.getProvider();
  }

}
