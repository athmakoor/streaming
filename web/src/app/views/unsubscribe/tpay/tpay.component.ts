import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, UrlTree, UrlSegmentGroup, PRIMARY_OUTLET, UrlSegment } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommonService } from 'src/app/services/data/common.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmComponent } from '../../dialog/confirm/confirm.component';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';

@Component({
  selector: 'app-tpay',
  templateUrl: './tpay.component.html',
  styleUrls: ['./tpay.component.css']
})
export class TpayComponent implements OnInit {
  bannerImage: string;
  mdn: any;
  provider: any;
  pack: any;
  paramData: any;
  msisdn: any;
  status: string;
  statusType: string;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router, private dialog: MatDialog,
              private readonly localStorageService: LocalStorageService,
              private readonly cookieService: AuthCookieService,  private readonly subscriptionService: SubscriptionService) {
    this.bannerImage = '/assets/img/banner-2.png';
    this.mdn = this.cookieService.getMdn();
    this.provider = this.cookieService.getProvider() || Config.TPAY;
    this.pack = localStorageService.getPackData(this.mdn);

    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;

      if (this.paramData.msisdn && this.paramData.msisdn !== '') {
        this.msisdn = this.paramData.msisdn;

        this.cookieService.setMdn(this.msisdn);
      }
    });
   }

  ngOnInit(): void {
  }

  onUnSubscribe(): void {
    const pData = {
      msisdn: decodeURIComponent(this.cookieService.getMdn()),
      provider: this.cookieService.getProvider(),
      packId: ''
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;

    const dialogRef = this.dialog.open(ConfirmComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => {
        if (data) {
          this.subscriptionService.unSubscribe(pData).subscribe(
            success => {
              if (success) {
                this.status = 'Unsubscription successfull';
                this.statusType = 'ok';
                this.clearMsisdn();
                this.router.navigate(['/' + this.cookieService.getProvider() + '/' + this.cookieService.getLocale() + '/games'], {
                });
              } else {
                this.status = 'Unsubscription Failed.Try again later.';
                this.statusType = 'error-message';
              }
            },
            error => {
              this.status = 'Failed to unsubscribe.Try Later.';
              this.statusType = 'error-message';
            }
          );
        }
      }
    );
  }

  clearMsisdn(): void {
    this.cookieService.setMdn('');
  }
}
