import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, PRIMARY_OUTLET, Router, UrlSegment, UrlSegmentGroup, UrlTree } from '@angular/router';
import { AuthCookieService } from 'src/app/common/services/cookie/auth-cookie.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { GamesService } from 'src/app/services/data/games.service';

@Component({
  selector: 'app-game-category-list',
  templateUrl: './game-category-list.component.html',
  styleUrls: ['./game-category-list.component.css']
})
export class GameCategoryListComponent implements OnInit {
  games: any;
  pageSize: any;
  mdn: any;
  hasSubScription: boolean;
  category: any;

  constructor(private readonly route: ActivatedRoute, private readonly gamesService: GamesService,
              private readonly router: Router, private readonly authService: AuthService,
              private readonly cookieService: AuthCookieService) {
    const tree: UrlTree = this.router.parseUrl(this.router.url);
    const g: UrlSegmentGroup = tree.root.children[PRIMARY_OUTLET];
    const s: UrlSegment[] = g.segments;

    this.getCategory(s);
  }

  ngOnInit(): void {
    this.gamesService.getVideosByCategory(this.category).subscribe(gameData => {
      this.games = gameData;
      this.pageSize = this.games.length;
    });

    this.route.queryParams.subscribe((params: any) => {
      this.mdn = this.cookieService.getMdn();

      if (this.mdn !== '') {
        this.authService.validateMdn(this.mdn).subscribe(
          data => {
            if (data.authenticated) {
              this.hasSubScription = true;
            } else {
              this.hasSubScription = false;
            }
          });
      } else {
        this.hasSubScription = false;
      }
    });
  }

  getCategory(segemnts: UrlSegment[]): void {
    if (segemnts.length > 1 && segemnts[1]) {
      this.category = segemnts[segemnts.length - 1].path;
    }
  }
}
