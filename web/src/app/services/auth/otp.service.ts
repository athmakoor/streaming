import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { apiURL } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OtpService {
  constructor(private readonly http: HttpClient) { }

  public validateOtp(data: any, otpval: any): Observable<any> {
    const addressData = {
      msisdn: data.msisdn,
      otpText: otpval
    };

    return this.http.post(`${apiURL}auth/verifyOTP`, addressData);
  }
}
