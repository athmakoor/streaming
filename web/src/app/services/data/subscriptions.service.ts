import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionsService {

  constructor(private readonly http: HttpClient) {
  }

  public getPlans(): Observable<any> {
    return this.http.get(`${apiURL}/subscriptions/all`);
  }

  checkContentPermissions(contentId, contentType, msisdn): Observable<any> {
    return this.http.get<any>(`${apiURL}/contentDetails?contentId=${contentId}&ContentType=${contentType}&msisdn=${msisdn}`);
  }

  getContentDetails(id: string): Observable<any> {
    return this.http.get<any>(`${apiURL}/api/video/id/${id}`);
  }
}
