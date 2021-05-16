import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';
import { ConfirmComponent } from '../../dialog/confirm/confirm.component';

@Component({
  selector: 'app-timwe',
  templateUrl: './timwe.component.html',
  styleUrls: ['./timwe.component.scss']
})
export class TimweComponent implements OnInit {

  bannerImage: string;
  mdn: any;
  provider: any;
  pack: any;
  paramData: any;
  msisdn: any;
  status: string;
  statusType: string;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router, private dialog: MatDialog,
              private readonly subscriptionService: SubscriptionService, private readonly localStorageService: LocalStorageService,
              private readonly cookieService: AuthCookieService, private readonly authService: AuthService) {
    this.bannerImage = Config.S3_ROOT_WITH_BUCKET + 'game-landing-banner.jpg';
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
                window.location.href = Config.APP_URL + '?msisdn=' + data.data + '&provider=' + Config.TIMWE;
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
