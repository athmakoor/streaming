import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { CookieNames } from '../../constants/cookie-names';

@Injectable({
  providedIn: 'root'
})
export class CustomCookieService {

  constructor(private readonly cookieService: CookieService) { }

  public setAccessToken(token: string): void {
      this.cookieService.set(CookieNames.ACCESS_TOKEN, token, undefined, '/');
  }

  public getAccessToken(): any {
    return this.cookieService.get(CookieNames.ACCESS_TOKEN);
  }

  public checkAccessToken(): any {
    return this.cookieService.check(CookieNames.ACCESS_TOKEN);
  }

  public deleteAccessToken(): void {
    this.cookieService.delete(CookieNames.ACCESS_TOKEN, '/');
  }

  public setProvider(provider: string): void {
    this.cookieService.set(CookieNames.CURRENT_PROVIDER, provider, undefined, '/');
  }

  public setMdn(mdn: string): void {
    this.cookieService.set(CookieNames.CURRENT_MDN, mdn, undefined, '/');
  }

  public setLocale(locale: string): void {
    this.cookieService.set(CookieNames.CURRENT_LOCALE, locale, undefined, '/');
  }

  public setLang(lang: string): void {
    this.cookieService.set(CookieNames.CURRENT_LANG, lang, undefined, '/');
  }

  public setOpCode(opCode: string): void {
    this.cookieService.set(CookieNames.OPERATOR_CODE, opCode, undefined, '/');
  }

  public getProvider(): string {
    return this.cookieService.get(CookieNames.CURRENT_PROVIDER);
  }

  public getMdn(): string {
    return this.cookieService.get(CookieNames.CURRENT_MDN);
  }

  public getLocale(): string {
    return this.cookieService.get(CookieNames.CURRENT_LOCALE);
  }

  public getLang(): string {
    return this.cookieService.get(CookieNames.CURRENT_LANG);
  }

  public getOpCode(): string {
    return this.cookieService.get(CookieNames.OPERATOR_CODE);
  }

  public setCookiePolicyAcceptance(token: string): void {
    this.cookieService.set(CookieNames.COOKIE_POLICY_ACCEPTED, token, undefined, '/');
  }

  public getCookiePolicyAcceptance(): any {
    return this.cookieService.get(CookieNames.COOKIE_POLICY_ACCEPTED);
  }

  public checkCookiePolicyAcceptance(): any {
    return this.cookieService.check(CookieNames.COOKIE_POLICY_ACCEPTED);
  }

  public deleteCookiePolicyAcceptance(): void {
    this.cookieService.delete(CookieNames.COOKIE_POLICY_ACCEPTED, '/');
  }

  public clearCookies(): void {
    this.cookieService.set(CookieNames.ACCESS_TOKEN, '', -1 , '/');
    this.cookieService.set(CookieNames.CURRENT_PROVIDER, '', -1, '/');
    this.cookieService.set(CookieNames.CURRENT_MDN, '', -1, '/');
    this.cookieService.set(CookieNames.GV_SESSION_KEY, '', -1, '/');
  }
}
