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
}
