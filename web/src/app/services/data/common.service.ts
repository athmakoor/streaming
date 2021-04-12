import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Urls } from 'src/app/common/constants/urls';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private readonly http: HttpClient) { }

  public getCountryByLocale(locale: string): Observable<any> {
    return this.http.get(`${Urls.OPERATORS_AND_COUNTRY_BY_LOCALE}${locale}`);
  }

  public getPacks(provider: string, operator: string): Promise<any> {
    const promise = new Promise((resolve, reject) => {
      this.http.get(`${Urls.PACKS_BY_OPERATOR}${provider}/${operator}`)
      .toPromise()
      .then(data => {
        resolve(data);
      },
      error => {
        reject(error);
      });
    });

    return promise;
  }

  public getCountryByOperatorCode(operatorCode: string): Promise<any>{
    const promise = new Promise((resolve, reject) => {
      this.http.get(`${Urls.OPERATORS_AND_COUNTRY_BY_OPERATORCODE}/${operatorCode}`)
      .toPromise()
      .then (
          res => {
            resolve(res);
          },
          err => {
            reject(err);
          }
      );
    });

    return promise;
  }
}
