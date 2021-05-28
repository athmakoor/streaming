import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Urls } from '../../common/constants/urls';
import { $ } from 'protractor';

@Injectable({
  providedIn: 'root'
})
export class GamesService {

  constructor(private readonly http: HttpClient) { }

  public getGames(): Observable<any> {
    return this.http.get(`${Urls.GAMES_GROUP_BY_CATEGORIES}`);
  }

  public getVideosByCategory(category: string): Observable<any> {
    return this.http.get(`${(Urls.HOME_VIDEOS_API_CATEGORY)}${category}`);
  }

  public getGameDetails(gameId: number): Observable<any> {
    return this.http.get(`${Urls.GAMES_BY_ID}${gameId}`);
  }

  public getHomeVideos(): Observable<any> {
    return this.http.get(`${Urls.HOME_VIDEOS_API}`);
  }
}
