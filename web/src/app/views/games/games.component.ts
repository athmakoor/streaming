import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Config } from 'src/app/common/constants/config';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { ScriptService } from 'src/app/common/services/page/script.service';
import { UrlParser } from 'src/app/common/utils/url-parser';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommonService } from 'src/app/services/data/common.service';
import { GamesService } from 'src/app/services/data/games.service';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';

declare var TPay: any;

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent implements OnInit {
  urlParser: any;
  bannerImage: string;
  games: any;
  pageSize: number;
  mdn: any;
  hasSubScription: boolean;
  hasHeScript = false;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router, private readonly gamesService: GamesService,
              private readonly dataService: CommonService, private readonly scriptService: ScriptService,
              private readonly cookieService: AuthCookieService, public subscriptionService: SubscriptionService,private readonly authService: AuthService) {
    this.urlParser = new UrlParser(cookieService);
    this.urlParser.extractValues(this.router, this.router.url, 'games');

    this.bannerImage = '/assets/img/banner-2.png';

    this.gamesService.getHomeVideos().subscribe(data => {
      this.games = data;
      this.pageSize = 6;
    });

    this.route.queryParams.subscribe((params: any) => {
      this.mdn = params.msisdn || this.cookieService.getMdn();

      if (this.mdn !== '') {
        this.subscriptionService.checkSubscription(this.mdn).subscribe(data => {
          if (data.serviceCode === 3) {
              this.hasSubScription = true;
              this.cookieService.setMdn(this.mdn);
            } else {
              this.hasSubScription = false;
            }
          });
      } else {
        this.hasSubScription = false;
      }
    });
  }
  ngOnInit(): void {
  }
}
