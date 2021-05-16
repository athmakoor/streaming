import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/common/constants/config';

import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { ScriptService } from 'src/app/common/services/page/script.service';
import { UrlParser } from 'src/app/common/utils/url-parser';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';

@Component({
  selector: 'app-timwe',
  templateUrl: './timwe.component.html',
  styleUrls: ['./timwe.component.scss']
})
export class TimweComponent implements OnInit {

  urlParser: UrlParser;
  paramData: any;
  extractedValues: any;
  validated = false;
  status: any;
  bannerImage: string;
  statusType: any;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router,
              private readonly cookieService: AuthCookieService, private readonly subscriptionService: SubscriptionService) {
    this.bannerImage = Config.S3_ROOT_WITH_BUCKET + 'game-landing-banner.jpg';
    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;
      this.verifyValues();
    });
  }

  verifyValues(): void {
    const keys = ['correlatorId', 'statusCode'];
    this.status = 'Validating your consent detail.Please wait few seconds..';
    this.statusType = 'ok';
    this.extractedValues = {};

    if (this.paramData) {
      Object.entries(this.paramData).forEach(
        ([key, value]) => {
          if (keys.indexOf(key) !== -1) {
            this.extractedValues[key] = value;
          }
        }
      );

      const requestData = {
        statusCode: this.extractedValues.statusCode,
        correlatorId: this.extractedValues.correlatorId
      };

      this.status = 'Adding Subscripton to your Mobile Number ';
      this.statusType = 'ok';

      this.subscriptionService.updateSubscriptionStatus(requestData).subscribe(data => {
        if (!data.hasError) {
          this.cookieService.setMdn(data.data);
          this.status = 'Subscription Added SuccessFully';
          this.statusType = 'ok';
          this.validated = true;
          window.location.href = Config.APP_URL + '?msisdn=' + data.data + '&provider=' + Config.TIMWE;
        } else {
          this.status = data.errorMessage;
          this.statusType = 'error-message';
        }
      },
        error => {
          this.status = 'Failed to validate your consent try later.';
          this.statusType = 'error-message';
        }
      );
    }

  }

  proceed(): void {
    this.router.navigate(['?msisdn=' + this.cookieService.getMdn() + '&provider=' + Config.TIMWE]);
  }

  ngOnInit(): void {
  }

}
