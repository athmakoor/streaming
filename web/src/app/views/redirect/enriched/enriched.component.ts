import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, PRIMARY_OUTLET, Router, UrlSegment, UrlSegmentGroup, UrlTree } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { ScriptService } from 'src/app/common/services/page/script.service';
import { UrlParser } from 'src/app/common/utils/url-parser';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';
import { CommonService } from 'src/app/services/data/common.service';

declare var TPay: any;

@Component({
  selector: 'app-enriched',
  templateUrl: './enriched.component.html',
  styleUrls: ['./enriched.component.css']
})
export class EnrichedComponent implements OnInit {
  urlParser: UrlParser;
  paramData: any;
  extractedValues: any;
  validated = false;
  status: any;
  bannerImage: string;
  statusType: any;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router,
              private readonly dataService: CommonService, private readonly localStorageService: LocalStorageService,
              private readonly cookieService: AuthCookieService, private readonly subscriptionService: SubscriptionService) {
    this.bannerImage = '/assets/img/banner-2.png';
    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;
      this.verifyValues();
    });
  }

  verifyValues(): void {
    const keys = ['lang', 'Status', 'OperatorCode', 'SessionId',
      'Msisdn', 'ReferenceCode', 'OrderId', 'Details', 'SubId', 'Signature'];
    this.status = 'Validating your consent detail.Please wait few seconds..';
    this.statusType = 'ok';
    this.extractedValues = {};

    if (this.paramData) {
      Object.entries(this.paramData).forEach(
        ([key, value]) => {
          if (value) {
            this.extractedValues[key] = value;
          }
        }
      );

      if (this.extractedValues.Status === 'Success' && this.extractedValues.ReferenceCode) {
        const requestData = {
          msisdn: this.extractedValues.Msisdn,
          provider: Config.TPAY,
          packId: this.extractedValues.OrderId,
          headerEnrichmentCode: this.extractedValues.ReferenceCode,
          sessionToken: this.extractedValues.SessionToken,
          sessionId: this.extractedValues.SessionId
        };

        if (requestData.packId) {
          this.setSelectedPack(requestData.msisdn, requestData.packId);
        }

        this.status = 'Adding Subscripton to your Mobile Number ';
        this.statusType = 'ok';

        this.subscriptionService.addSubscriptionContract(requestData).subscribe(data => {
          if (data.errCode === '0') {
            this.cookieService.setMdn(this.extractedValues.Msisdn);
            this.status = 'Subscription Added SuccessFully';
            this.statusType = 'ok';
            this.validated = true;
            this.router.navigate(['/' + this.cookieService.getProvider() + '/' +  this.cookieService.getLocale() + '/games/']);
          } else if (data.errCode === '51' && data.msisdn) {
            this.cookieService.setMdn(this.extractedValues.Msisdn);
            this.router.navigate(['/' + this.cookieService.getProvider() + '/' +  this.cookieService.getLocale() + '/games/']);
          }
        },
          error => {
            this.status = 'Failed to validate your consent try later.';
            this.statusType = 'error-message';
          }
        );
      }
    } else {
      console.log('Parameters are missing');
      this.status = 'Failed to validate your consent try later.';
      this.statusType = 'error-message';
    }
  }

  proceed(): void {
    this.router.navigate(['/' + this.cookieService.getProvider() + '/' + this.cookieService.getLocale() + '/games']);
  }

  ngOnInit(): void {
  }

  setSelectedPack(msisdn: string, packId: string): void {
    this.dataService.getPacks(this.cookieService.getProvider(), this.cookieService.getOpCode())
      .then(data => {

        for (const d of data) {
          if (d.sku === packId) {
            this.localStorageService.setPackData(msisdn, d);
          }
        }
      });
  }
}
