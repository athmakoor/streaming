import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { OtpService } from 'src/app/services/auth/otp.service';
import { timer, from } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Config } from 'src/app/common/constants/config';
import { LocalStorageService } from 'src/app/common/services/localStorage/localstorage.service';

@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent implements OnInit {
  otpText: FormControl;
  paramData: any;
  data: any;
  interv: any;
  time: any;
  validating: boolean;
  timeOut: boolean;
  bannerImage: string;
  pack: any;
  otpForm: FormGroup;
  status: string;
  statusType: string;
  pattern: string;
  locale: any;
  provider: any;
  mdn: any;
  timer: number;
  timeInterval: any;
  reTryCount = 5;
  countDown: boolean;

  constructor(private readonly route: ActivatedRoute, private readonly otpValidate: OtpService,
              private readonly cookieService: AuthCookieService, private readonly authService: AuthService,
              private readonly router: Router, private readonly localStorageService: LocalStorageService) {
    this.bannerImage = '/assets/img/banner-2.png';
    this.mdn = this.cookieService.getMdn();
    this.pack =  localStorageService.getPackData(this.mdn);
    this.locale = this.cookieService.getLocale();
    this.provider = this.cookieService.getProvider();
    this.countDown = false;
    this.pattern = '^[0-9]*$';
  }

  ngOnInit(): void {
    this.otpForm = new FormGroup({
      otpText: new FormControl('', [Validators.required, Validators.minLength(4),
        Validators.maxLength(6), Validators.pattern(this.pattern)])
    });
    this.validating = false;
    this.time = 0;
    this.timeOut = false;
    this.statusType = 'ok';
  }

  onSubmit(): void {
    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;
      this.data = {
        msisdn: this.cookieService.getMdn(),
        provider: this.cookieService.getProvider()
      };

      this.validating = true;
      this.status = 'Verifing OTP...';
      this.statusType = 'ok';
      this.otpValidate.validateOtp(this.data, this.otpFormControl.otpText.value).subscribe(
        data => {
          if (data) {
            this.checkSubscription();
          } else {
            this.status = 'Otp Validation Failed';
            this.statusType = 'error';
            this.validating = false;
            this.timeOut = true;
            this.reset();
          }
        },
        err => {
          this.status = 'Otp Validation Failed';
          this.statusType = 'error';
          this.validating = false;
          this.timeOut = true;
          this.reset();
        });
    });
  }

  get otpFormControl(): any {
    return this.otpForm.controls;
  }

  reset(): void {
    this.otpForm.reset();
  }

  checkSubscription(): void {
    this.interv = setInterval(() => {
      this.time += 1000;
      this.status = 'Updating your subscription';
      this.authService.validateMdn(this.cookieService.getMdn()).subscribe(
        data => {
          if (data.authenticated) {
            clearInterval(this.interv);
            this.validating = false;
            this.status = 'Subscription success';
            this.router.navigate(['/' + this.provider + '/' + this.locale + '/games'], {
            });
          } else if (this.time > 9000) {
            this.validating = false;
            this.status = 'Subscription update will take time check later';
            this.statusType = 'error';
            clearInterval(this.interv);
            this.timeOut = true;
            this.reset();
          }
        }
      );
    }, 2000);
  }

  reGenrateOtp(): void {
    this.reTryCount -= 1;
    this.timer = 30000;
    this.countDown = true;
    this.timeOut = false;

    if (this.reTryCount >= 0) {
      this.timeInterval = setInterval(() => {
        this.timer -= 1000;

        if (this.timer === 0) {
          this.countDown = false;
          this.timeOut = true;
          clearTimeout(this.timeInterval);
        }
      }, 1000);
    } else {
      this.status = 'Only 5 retrials allowed. Try later';
      this.statusType = 'error';
      this.reset();
      this.timeOut = false;
    }

    this.authService.reGenerateOTP(this.cookieService.getMdn()).subscribe(data => {
      if (data.otpSent) {
        this.status = 'Otp resent to mobile number';
        this.statusType = 'ok';
      }
    }, error => {
      this.status = 'Otp sending failed to mobile number';
      this.statusType = 'error';
      this.timeOut = true;
      this.reset();

      if (this.timeInterval) {
        clearTimeout(this.timeInterval);
      }
    });
  }
}
