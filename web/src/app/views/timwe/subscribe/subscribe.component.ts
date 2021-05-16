import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { Languages } from 'src/app/common/constants/languages';
import { Mobile } from 'src/app/common/constants/mobile';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { UrlParser } from 'src/app/common/utils/url-parser';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';
import { CommonService } from 'src/app/services/data/common.service';
import { MessageComponent } from '../../dialog/message/message.component';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.scss']
})
export class SubscribeComponent implements OnInit {

  bannerImage: string;
  mdn: FormControl;
  operator: FormControl;
  package: FormControl;
  paramData: any;
  status: string;
  statusType: string;
  rData: any;
  unSub: any;
  subscribeOp: boolean;
  subscribe: FormGroup;
  mobile: any;
  country: any;
  languages: any;
  urlParser: UrlParser;
  packs: any;
  selectedPack: any;
  msisdn: any;

  hasHeScript = false;
  isHe = false;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router, private dialog: MatDialog,
              private readonly dataService: CommonService, private readonly localStorageService: LocalStorageService,
              private readonly cookieService: AuthCookieService, private readonly authService: AuthService,
              private readonly subsciptionService: SubscriptionService) {

    this.urlParser = new UrlParser(cookieService);
    this.urlParser.extractValues(this.router, this.router.url, 'subscribe');
    this.packs = [];

    this.bannerImage = Config.S3_ROOT_WITH_BUCKET + 'game-landing-banner.jpg';
    this.subscribeOp = false;

    if (this.localStorageService.getContent(this.cookieService.getLocale())) {
      this.country = this.localStorageService.getContent(this.cookieService.getLocale());
      this.mobile = Mobile.byCountry(this.country.country.country);
      this.subscribe = new FormGroup({
        operator: new FormControl('', [Validators.required]),
        mdn: new FormControl('', [Validators.required, Validators.pattern(this.mobile.pattern)]),
        package: new FormControl('', [Validators.required])
      });

      this.subscribeOp = true;
    } else {
      this.dataService.getCountryByLocale(this.cookieService.getLocale()).subscribe(
        data => {
          if (data) {
            this.country = data;
            this.localStorageService.setContent(this.cookieService.getLocale(), this.country);
            this.mobile = Mobile.byCountry(this.country.country.country);
            this.subscribe = new FormGroup({
              operator: new FormControl('', [Validators.required]),
              mdn: new FormControl('', [Validators.required, Validators.pattern(this.mobile.pattern)]),
              package: new FormControl('', [Validators.required])
            });

            this.subscribeOp = true;
          }
        }
      );
    }

    this.languages = Languages.all;

    this.msisdn = this.cookieService.getMdn();

    if (this.msisdn && this.msisdn !== '') {
      this.subsciptionService.checkSubscription(this.msisdn).subscribe(
        data => {
          if (data.serviceCode === 3) {
            window.location.href = Config.APP_URL + '?msisdn=' + data.data + '&provider=' + Config.TIMWE;
          } else if (data.serviceCode === 8) {
            const dialogConfig = new MatDialogConfig();
            dialogConfig.autoFocus = true;
            dialogConfig.data = {message: data.message };

            this.dialog.open(MessageComponent, dialogConfig);
          } else {
            const dialogConfig = new MatDialogConfig();
            dialogConfig.autoFocus = true;
            dialogConfig.data = {message: data.message };

            const dialogRef = this.dialog.open(MessageComponent, dialogConfig);

            dialogRef.afterClosed().subscribe(d => {
              if (d) {
                window.location.href = Config.APP_URL + '?msisdn=' + data.data + '&provider=' + Config.TIMWE;
              }
            });
          }
        }
      );
    }
  }

  ngOnInit(): void {
    this.unSub = Config.UNSUBS[this.cookieService.getProvider()];
    this.statusType = 'ok';
  }

  onSubmit(): void {
    this.status = 'Validating registered number..';
    this.statusType = 'ok';

    this.rData = {
      msisdn: this.mobile.code + this.subscribeFormControl.mdn.value,
      packId: this.subscribeFormControl.package.value,
      locale: this.cookieService.getLocale()
    };

    this.subsciptionService.checkSubscription(this.rData.msisdn).subscribe(
      data => {
        if (data.serviceCode === 1 || data.serviceCode === 8) {
          this.cookieService.setMdn(this.rData.msisdn);
          this.authService.getConsentUrl(this.rData).subscribe(
            cdata => {
              this.statusType = 'ok';
              if (!cdata.hasError) {
                this.cookieService.setMdn(this.rData.msisdn);
                this.localStorageService.setPackData(this.rData.msisdn, this.selectedPack);

                this.status = 'You Will be redirecting to consent page shortly';

                if (cdata.serviceCode === 2) {
                  window.location.href = cdata.data;
                } else {
                  const dialogConfig = new MatDialogConfig();
                  dialogConfig.autoFocus = true;
                  dialogConfig.data = {message: cdata.message };

                  const dialogRef = this.dialog.open(MessageComponent, dialogConfig);

                  dialogRef.afterClosed().subscribe(d => {
                    if (d) {
                      window.location.href = Config.APP_URL + '?msisdn=' + data.data + '&provider=' + Config.TIMWE;
                    }
                  });
                }
              }
            },
            err => {
              this.status = 'Failed Plaese Try Later';
              this.statusType = 'error';
            });
        } else {
          this.status = data.message;
          this.cookieService.setMdn(this.rData.msisdn);
          this.localStorageService.setPackData(this.rData.msisdn, this.selectedPack);
          window.location.href = Config.APP_URL + '?msisdn=' + data.data + '&provider=' + Config.TIMWE;
        }
      });
  }

  get subscribeFormControl(): any {
    return this.subscribe.controls;
  }

  onLangChange(): void {

  }

  onPackChange(value: any): void {
    this.selectedPack = value;
  }

  onOperatorChange(value: any): void {
    this.packs = [];
    this.selectedPack = null;
    this.dataService.getPacks(this.cookieService.getProvider(), value.operatorCode)
      .then(data => {
        this.packs = data;
      });
  }

  onSubscribe(): void {
    this.subscribeOp = true;
  }

}
