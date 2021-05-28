import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout/layout/layout.component';
import { FaqComponent } from './views/info/faq/faq.component';
import { PrivacyComponent } from './views/info/privacy/privacy.component';
import { TncComponent } from './views/info/tnc/tnc.component';
import { Config } from 'src/app/common/constants/config';
import { ErrorComponent } from './views/error/error.component';
import { HomeComponent } from './views/home/home.component';
import { GameCategoryListComponent } from './views/games/game-category-list/game-category-list.component';

const routes: Routes = [{
  path: '',
  component: LayoutComponent,
  children: [
    { path: 'tpay', loadChildren: () => import('./views/tpay/tpay.module').then(m => m.TpayModule) },
    { path: 'timwe', loadChildren: () => import('./views/timwe/timwe.module').then(m => m.TimweModule) },
    {
      path: Config.TPAY + '/unsubscribe', loadChildren: () => import('./views/unsubscribe/tpay/tpay.module').then(m => m.TpayModule)
    },
    {
      path: Config.TIMWE + '/unsubscribe', loadChildren: () => import('./views/unsubscribe/timwe/timwe.module').then(m => m.TimweModule)
    },
    {
      path: ':provider/:locale/games', loadChildren: () => import('./views/games/games.module').then(m => m.GamesModule)
    },
    {
      path: 'games', loadChildren: () => import('./views/games/games.module').then(m => m.GamesModule)
    },
    {
      path: '', loadChildren: () => import('./views/games/games.module').then(m => m.GamesModule)
    },
    {
      path: 'home', loadChildren: () => import('./views/games/games.module').then(m => m.GamesModule)
    },
    {
      path: 'category/:category', component: GameCategoryListComponent
    },
    {
      path: ':provider/:locale/games/category/:category', component: GameCategoryListComponent
    },
    { path: 'game', loadChildren: () => import('./views/games/game/game.module').then(m => m.GameModule)
    },
    {
      path: 'otp', loadChildren: () => import('./views/otp/otp.module').then(m => m.OtpModule)
    },
    {
      path: 'redirect/enriched', loadChildren: () => import('./views/redirect/enriched/enriched.module').then(m => m.EnrichedModule)
    },
    {
      path: 'redirect/subscribe/' + Config.TIMWE , loadChildren: () =>
                       import('./views/redirect/timwe/timwe.module').then(m => m.TimweModule)
    },
    {
        path: 'video',
        loadChildren: () => import('./views/video/video.module').then(module => module.VideoModule)
    },
    {
      path: 'info/faq',
      component: FaqComponent
    }, {
      path: 'info/privacy',
      component: PrivacyComponent
    }, {
      path: 'info/tnc',
      component: TncComponent
    },
    {
      path        : '**',
      pathMatch   : 'full',
      component   : ErrorComponent
   }]
}];

@NgModule({
  imports: [RouterModule.forRoot(routes , { enableTracing: false })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
