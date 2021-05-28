import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Urls } from 'src/app/common/constants/urls';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly http: HttpClient) { }

  public validateMdn(msisdn: string): Observable<any> {
    return this.http.get(`${Urls.AUTH_API_CHECK_MDN}?msisdn=${encodeURIComponent(msisdn)}`);
  }

  public unSubscribe(data: any): Observable<any> {
    return this.http.post(`${Urls.UN_SUBSCRIBE}`, data);
  }


  public generateOTP(data: any): Observable<any> {
    return this.http.post(`${Urls.CHECK_AND_GENERATE_OTP}`, this.getRequestBody(data));
  }

  public reGenerateOTP(msisdn: string): Observable<any> {
    return this.http.get(`${Urls.REGENERATE_OTP}${msisdn}`);
  }

  public getHeSource(lang: string, operator: string, msisdn: string): Observable<any> {
    return this.http.get(`${Urls.HE_SOURCE}${lang}/${operator}/${msisdn}`);
  }

  public getHeSourceByLang(lang: string): Observable<any> {
    return this.http.get(`${Urls.HE_SOURCE}${lang}`);
  }

  public getSignature(paramString: string): Observable<any> {
    return this.http.get(`${Urls.DIGEST}?message=${paramString}`);
  }

  public getDigest(paramString: string): Observable<any> {
    return this.http.get(`${Urls.SIGNATURE}?message=${paramString}`);
  }

  public getConsentUrl(data: any): Observable<any> {
    return this.http.post(`${Urls.TIMWE_CONSENT_URL}`, data);
  }

  public verifyOTP(data: any): Observable<any> {
    const rBody = {
      msisdn: data.msisdn,
      otpText: data.otp
    };

    return this.http.post(`${Urls.VERIFY_OTP}`, rBody);
  }

  private getRequestBody(data: any): any {
    const rBody = {
      msisdn: data.msisdn,
      provider: data.provider,
      packId: data.packId
    };

    return rBody;
  }
}
