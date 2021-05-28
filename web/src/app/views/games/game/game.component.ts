import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { UrlParser } from 'src/app/common/utils/url-parser';
import { SubscriptionService } from 'src/app/services/auth/subscription.service';
import { GamesService } from 'src/app/services/data/games.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  urlParser: UrlParser;
  mdn: any;
  hasSubScription: boolean;
  url: any;

  constructor(private readonly route: ActivatedRoute, private readonly subscriptionService: SubscriptionService,
              private readonly cookieService: AuthCookieService, private readonly router: Router,
              private readonly gamesService: GamesService, private dom: DomSanitizer) {
    this.mdn = this.cookieService.getMdn();

    this.route.queryParams.subscribe((params: any) => {
    if (this.mdn !== '' && params.id !== '') {
      this.subscriptionService.checkSubscription(this.mdn).subscribe(
        data => {
          if (data.serviceCode === 3) {
            this.cookieService.setMdn(this.mdn);
            this.gamesService.getGameDetails(params.id).subscribe(game => {
              this.url = this.dom.bypassSecurityTrustResourceUrl(game.gameUrl);
            });
          } else {
            this.router.navigate(['/'], {
            });
          }
        });
    } else {

    }
  });
  }

  ngOnInit(): void {
  }

}
