import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { CustomCookieService } from './custom-cookie.service';

@Injectable({
  providedIn: 'root'
})
export class AuthCookieService {
  authenticated: boolean = undefined;

  hasProvider: boolean = undefined;
  providerChange: Subject<boolean> = new Subject<boolean>();

  hasMdn: boolean = undefined;
  mdnChange: Subject<boolean> = new Subject<boolean>();

  localeChange: Subject<boolean> = new Subject<boolean>();

  private privateNextRouterLink: string;

  constructor(private readonly customCookieService: CustomCookieService) {
    this.providerChange.subscribe((value: boolean) => {
      this.hasProvider = value;
    });

    this.mdnChange.subscribe((value: boolean) => {
      this.hasMdn = value;
    });
  }

  public setAccessToken(token: string): void {
    this.customCookieService.setAccessToken(token);
  }

  public setIsAuthenticated(val: boolean): void {
    this.authenticated = val;
  }

  public setProvider(provider: string): void {
    if (provider && !this.checkProvider(provider)) {
      this.customCookieService.setProvider(provider);
      this.setProviderChanged(true);
    }
  }

  public setMdn(mdn: string): void {
    if (mdn && !this.checkMdn(mdn)) {
      this.customCookieService.setMdn(mdn);
      this.setMdnChanged(true);
    }
  }

  public setLocale(locale: string): void {
    if (locale) {
      this.customCookieService.setLocale(locale);
    }
  }

  public setLang(lang: string): void {
    if (lang) {
      this.customCookieService.setLang(lang);
    }
  }

  public setOpcode(code: string): void {
    if (code) {
      this.customCookieService.setOpCode(code);
    }
  }

  private checkProvider(provider: string): boolean {
    return false;
  }

  private checkMdn(provider: string): boolean {
    return false;
  }

  public getProvider(): any {
    return this.customCookieService.getProvider();
  }

  public getMdn(): any {
    return this.customCookieService.getMdn();
  }

  public getLocale(): any {
    return this.customCookieService.getLocale();
  }

  public getLang(): any {
    return this.customCookieService.getLang();
  }

  public getOpCode(): any {
    return this.customCookieService.getOpCode();
  }

  private clearCookies(): void {
    this.customCookieService.clearCookies();
  }

  public isAuthenticated(): boolean {
    return this.authenticated;
  }

  setProviderChanged(value: boolean): void {
    this.providerChange.next(value);
    this.setMdn('');
  }

  setMdnChanged(value: boolean): void {
    this.mdnChange.next(value);
  }

  get nextRouterLink(): string {
    return this.privateNextRouterLink || '';
  }

  setNextRouterLink(value: string): void {
    this.privateNextRouterLink = value;
  }
}
