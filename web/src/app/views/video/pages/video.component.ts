import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import videojs from 'video.js';
import { MatDialog } from '@angular/material/dialog';

import { SubscriptionsService } from 'src/app/services/data/subscriptions.service';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { GamesService } from 'src/app/services/data/games.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';

@Component({
selector: 'app-video',
templateUrl: './video.component.html',
styleUrls: ['./video.component.scss']
})
export class VideoComponent implements OnInit, OnDestroy {

content: any;
player: videojs.Player;
isMyList = false;
isLiked = false;
url:any;
relatedVideos:any;
pageSize: number;
category: any;

constructor(private readonly _activatedRoute: ActivatedRoute,
    public dialog: MatDialog,
    private readonly gamesService: GamesService,
    public _subscriptionService: SubscriptionsService,
    public subscriptionService: SubscriptionService,
    private readonly _router: Router, private readonly cookieService: AuthCookieService) {

    this._router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };

    this._activatedRoute.queryParams.subscribe(params => {
      let msisdn = this.cookieService.getMdn();
      if (msisdn) {
        this.subscriptionService.checkSubscription(msisdn).subscribe(data => {
          if (data.serviceCode === 3) {
            let contentId = params.id;
            this.getContentDetails(contentId);
          } else {
            this._router.navigate(['/timwe/ar_AE/subscribe'], {});
          }
        });
      } else {
        this._router.navigate(['/timwe/ar_AE/subscribe'], {});
      }
    });
  }

  ngOnInit(): void {
  }

  getContentDetails(contentId): void {
    this._subscriptionService.getContentDetails(contentId).subscribe(res => {
      this.url = "https://mooddit.s3.ap-south-1.amazonaws.com/" + res.category + "/compressed/" + res.videoUrl;
      this.getRelatedVideos(res.category);
    });
  }

  getRelatedVideos(contentType: string): void {
    this.gamesService.getVideosByCategory(contentType).subscribe(gameData => {
      this.relatedVideos = gameData;
      this.pageSize = this.relatedVideos.length;
    });
  }

  ngOnDestroy() {
  }
}
