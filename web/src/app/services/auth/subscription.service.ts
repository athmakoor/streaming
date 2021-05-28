import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { apiURL } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Urls } from 'src/app/common/constants/urls';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private readonly http: HttpClient) { }

  public checkSubscription(msisdn: string): Observable<any> {
    return this.http.get(`${Urls.CHECK_SUBSCRIPTION_STATUS}?msisdn=${encodeURIComponent(msisdn)}`);
  }


  public addSubscriptionContract(data: any): Observable<any> {
    return this.http.post(`${Urls.ADD_SUBSCRIPTION_CONTRACT}`, data);
  }

  // Timwe redirection
  public updateSubscriptionStatus(data: any): Observable<any> {
    return this.http.post(`${Urls.UPDATE_SUBSCRIPTION_STATUS}`, data);
  }

  public unSubscribe(data: any): Observable<any> {
    return this.http.post(`${Urls.TIM_UN_SUBSCRIBE}`, data);
  }

  public subscribePlan(data: any): Observable<any> {
    const subscripData = {
      msisdn: data.msisdn,
      transactionId: data.transactionId
    };

    return this.http.post(`${apiURL}/subscribe`, subscripData);
  }
}

