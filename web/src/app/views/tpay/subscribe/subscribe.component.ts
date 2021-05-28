import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Config } from 'src/app/common/constants/config';
import { Mobile } from 'src/app/common/constants/mobile';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { ScriptService } from 'src/app/common/services/page/script.service';
import { UrlParser } from 'src/app/common/utils/url-parser';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';
import { CommonService } from 'src/app/services/data/common.service';
import { MessageComponent } from '../../dialog/message/message.component';

declare var TPay: any;

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {
  bannerImage: string;
  mdn: FormControl;
  operator: FormControl;
  package: FormControl;
  enPack: FormControl;
  paramData: any;
  status: string;
  statusType: string;
  rData: any;
  unSub: any;
  subscribe: FormGroup;
  enrichForm: FormGroup;
  mobile: any;
  country: any;
  languages: any;
  urlParser: UrlParser;
  packs: any;
  selectedPack: any;
  selectedOperator: any;
  msisdn: any;
  selectedPac: any;


  hasHeScript = false;
  isEnriched = false;
  subscribeOp = false;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router,
              private readonly subsciptionService: SubscriptionService, private dialog: MatDialog,
              private readonly dataService: CommonService, private readonly localStorageService: LocalStorageService,
              private readonly cookieService: AuthCookieService, private readonly authService: AuthService,
              private readonly scriptService: ScriptService, public translate: TranslateService) {

    this.urlParser = new UrlParser(cookieService);
    this.urlParser.extractValues(this.router, this.router.url, 'subscribe');
    this.packs = [];
    this.unSub = Config.UNSUBS.tpay;

    this.bannerImage = '/assets/img/banner-2.png';

    if (!this.scriptService.isScriptAdded()) {
      this.route.queryParams.subscribe(qParams => {
        this.paramData = qParams;

        if (this.paramData.he && this.paramData.operatorcode !== '') {
          this.authService.getHeSource(translate.getDefaultLang(), this.paramData.operatorcode, this.paramData.msisdn).subscribe(data => {
            if (data.length > 0) {
              this.scriptService.add('heSource', data[0]);
              this.scriptService.load().then(success => {
                this.scriptService.heLoaded.next(true);
              });
            }
          });
        } else {
          this.authService.getHeSourceByLang(translate.getDefaultLang()).subscribe(data => {
            if (data.length > 0) {
              this.scriptService.add('heSource', data[0]);
              this.scriptService.load().then(success => {
                this.scriptService.heLoaded.next(true);
              });
            }
          });
        }
      });
    }

    this.dataService.getCountryByLocale(this.cookieService.getLocale()).subscribe(
      data => {
        if (data) {
          this.country = data;
          this.localStorageService.setContent(this.cookieService.getLocale(), this.country);
          this.mobile = Mobile.byCountry(this.country.country.country);
          this.subscribe = new FormGroup({
            operators: new FormControl('', [Validators.required]),
            mdn: new FormControl('', [Validators.required, Validators.pattern(this.mobile.pattern)]),
            package: new FormControl('', [Validators.required])
          });
        }
      }
    );

    this.msisdn = this.cookieService.getMdn();

    if (this.msisdn && this.msisdn !== '') {
      this.subsciptionService.checkSubscription(this.msisdn).subscribe(
        data => {
          if (data.serviceCode === 3) {
            this.router.navigate(['/' + this.cookieService.getProvider() + '/' +
                 this.cookieService.getLocale() + '/games'], {
            });
          } else if (data.serviceCode === 8 || data.serviceCode === 1) {
            const dialogConfig = new MatDialogConfig();
            dialogConfig.autoFocus = true;
            dialogConfig.data = {message: data.message };

            this.dialog.open(MessageComponent, dialogConfig);
            this.setAuthFlow();
          } else {
            const dialogConfig = new MatDialogConfig();
            dialogConfig.autoFocus = true;
            dialogConfig.data = {message: data.message };

            const dialogRef = this.dialog.open(MessageComponent, dialogConfig);

            dialogRef.afterClosed().subscribe(d => {
              if (d) {
                this.router.navigate(['/' + this.cookieService.getProvider() + '/' +
                this.cookieService.getLocale() + '/games'], {
              });
              }
            });
          }
        }
      );
    } else {
      this.setAuthFlow();
    }
  }

  ngOnInit(): void {
    this.unSub = Config.UNSUBS[this.cookieService.getProvider()];
    // this.selectedOperator = this.country.operators[0];
    // this.onOperatorChange(this.selectedOperator);
    this.statusType = 'ok';
  }

  setAuthFlow(): void {
    this.scriptService.getHeLoaded().subscribe(val => {
      this.hasHeScript = true;

      this.enrichForm = new FormGroup({
        enPack: new FormControl('', [Validators.required])
      });

      if (this.hasHeScript) {
        this.doEnrichment();
      } else {
        this.validateUser(false);
      }
    });
  }

  setEnriched(): void {
    this.isEnriched = true;
    this.subscribeOp = false;
  }

  setPinCodeFlow(): void {
    this.isEnriched = false;
    this.subscribeOp = true;
  }

  setSelectedOperator(code: string, operators: any): void {
    if (operators && operators.length > 0) {
      for (const op of operators) {
        if (op.operatorCode === code) {
          this.setSelectedOperator = op;

          break;
        }
      }
    }
  }

  onSubmit(): void {
    this.status = 'Validating registered number..';
    this.statusType = 'ok';

    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;
      this.rData = {
        msisdn: this.mobile.code + this.subscribeFormControl.mdn.value,
        packId: this.subscribeFormControl.package.value,
        provider: this.cookieService.getProvider()
      };

      this.cookieService.setMdn(this.rData.msisdn);

      this.authService.generateOTP(this.rData).subscribe(
        data => {
          this.statusType = 'ok';
          if (!data.authenticated && data.otpSent) {
            this.cookieService.setMdn(this.rData.msisdn);
            this.cookieService.setIsAuthenticated(data.authenticated);
            this.localStorageService.setPackData(this.rData.msisdn, this.selectedPack);

            this.status = 'OTP has been sent to registered number';

            if (data) {
              this.router.navigate(['/otp'], {
                relativeTo: this.route,
                skipLocationChange: true,
                queryParamsHandling: 'merge'
              });
            }
          } else if (data.authenticated && !data.otpSent) {
            this.cookieService.setMdn(this.rData.msisdn);
            this.cookieService.setIsAuthenticated(data.authenticated);
            this.router.navigate(['/' + this.cookieService.getProvider() + '/' + this.cookieService.getLocale() + '/games'], {
            });
          }
        },
        err => {
          this.status = 'Mobile Number validation failed';
          this.statusType = 'error';
        });
    });
  }

  get subscribeFormControl(): any {
    return this.subscribe.controls;
  }

  validateUser(hasTPay: boolean): void {
    this.msisdn = this.cookieService.getMdn();

    if (hasTPay) {
      this.msisdn = TPay.HeaderEnrichment.msisdn();
      this.cookieService.setMdn(this.msisdn);
    }

    if (this.msisdn && this.msisdn !== '') {
      this.authService.validateMdn(this.msisdn).subscribe(
        data => {
          if (data.authenticated) {
            this.router.navigate(['/' + this.cookieService.getProvider() + '/' + this.cookieService.getLocale() + '/games'], {
            });
          } else {
            this.setPinCodeFlow();
          }
        }
      );
    } else {
      this.setPinCodeFlow();
    }
  }

  onPackChange(value: any): void {
    this.selectedPack = value;
    this.selectedPac = value.sku;
  }

  onOperatorChange(value: any): void {
    this.packs = [];
    this.dataService.getPacks(this.cookieService.getProvider(), value.operatorCode)
      .then(data => {
        this.selectedOperator = value;
        this.packs = data;
        this.onPackChange(this.packs[0]);
      });
  }

  doEnrichment(): void {
    try {
      this.isEnriched = TPay.HeaderEnrichment.enriched();
      const operatorcode = TPay.HeaderEnrichment.operatorCode();
      const msisdn = TPay.HeaderEnrichment.msisdn() || this.cookieService.getMdn();

      if (this.isEnriched && operatorcode && !(/\S/.test(msisdn))) {
        this.setPackData(operatorcode);
      } else if (this.isEnriched && operatorcode && (/\S/.test(msisdn))) {
        this.checkSub(msisdn);
        this.cookieService.setOpcode(operatorcode);
      } else {
        this.setPinCodeFlow();
      }
    } catch (error) {
      console.log(error.message);
      this.setPinCodeFlow();
    }
  }

  onSubscribe(): void {
    this.checkSubscription(this.selectedPack);
  }

  checkSub(msisdn: string): any {
    if (msisdn) {
      const selectedPack = this.localStorageService.getPackData(msisdn);

      if (selectedPack) {
        this.checkSubscription(selectedPack);
      } else {
        const operatorcode = TPay.HeaderEnrichment.operatorCode();
        this.setPackData(operatorcode);
      }
    }
  }

  checkSubscription(selectedPack: any): void {
    TPay.HeaderEnrichment.hasSubscription(selectedPack.sku, (hasSub, subId) => {
      if (!hasSub) {
        this.doEnrichmentByConsent(selectedPack);
      } else {
        this.validateUser(true);
      }
    });
  }

  setPackData(operatorCode: string): void {
    this.dataService.getCountryByOperatorCode(operatorCode)
      .then(data => {
        if (data) {
          this.country = data;
          this.cookieService.setLocale(this.country.country.locale);
          this.localStorageService.setContent(this.cookieService.getLocale(), this.country);
          this.mobile = Mobile.byCountry(this.country.country.country);

          this.setSelectedOperator(operatorCode, this.country.operators);

          this.dataService.getPacks(this.cookieService.getProvider(), operatorCode)
            .then(packsData => {
              this.packs = packsData;

              this.cookieService.setOpcode(operatorCode);

              if (this.packs.length > 0) {
                this.setEnriched();
              } else if (this.packs.length === 1) {
                this.onPackChange(this.packs[0]);
                this.checkSubscription(this.selectedPack);
              }
            })
            .catch(error => {
              this.setPinCodeFlow();
            });
        }
      })
      .catch(err => {
        this.setPinCodeFlow();
      });
  }

  changeLanguage(lang: any): void {
  }

  doEnrichmentByConsent(pack: any): void {
    const messege = pack.sku + pack.catalogueName + pack.sku + pack.sku;

    this.authService.getSignature(messege).subscribe(digest => {
      TPay.HeaderEnrichment.consent(pack.sku,
        pack.catalogueName,
        pack.productId,
        pack.sku,
        Config.REDIRECT_URL,
        'en',
        digest);
    });
  }
}
