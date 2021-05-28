import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Config } from './../../../common/constants/config';

@Component({
  selector: 'app-game-card',
  templateUrl: './game-card.component.html',
  styleUrls: ['./game-card.component.css']
})
export class GameCardComponent implements OnInit {
  @Input() gamesGroup: any;
  @Input() hasSubScription: any;
  @Input() pageSize: any;
  bucketUrl: any;
  path: any;
  paramData: any;
  mdn: any;
  provider: any;
  locale: string;

  constructor(private readonly route: ActivatedRoute, private readonly router: Router, private readonly authService: AuthService,
              private readonly cookieService: AuthCookieService) {
    //this.bucketUrl = Config.S3_ROOT_WITH_BUCKET;
    this.bucketUrl = "https://mooddit.s3.ap-south-1.amazonaws.com/";
    this.path = "/icons/";
    this.mdn = this.cookieService.getMdn();
    this.provider = this.cookieService.getProvider();
    this.locale = this.cookieService.getLocale();
  }

  ngOnInit(): void {
  }

  onPlay(game: any): void {
    // if (this.hasSubScription) {
    //   this.authService.validateMdn(this.mdn).subscribe(
    //     data => {
    //       if (data.authenticated) {
            this.router.navigate(['/video'], {
              queryParams: {id: game.id}
            });

            // window.open(game.gameUrl, '_blank');
    //       } else {
    //         if (this.locale && this.provider) {
    //           this.router.navigate(['/' + this.provider + '/' + this.locale + '/subscribe']);
    //         }
    //       }
    //     });
    // } else if (this.locale && this.provider) {
    //   this.router.navigate(['/' + this.provider + '/' + this.locale + '/subscribe']);
    // }
  }
}
