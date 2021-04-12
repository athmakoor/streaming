import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Config } from './common/constants/config';
import { LayoutComponent } from './layout/layout/layout.component';
import { ErrorComponent } from './views/error/error.component';
import { HomeComponent } from './views/home/home.component';
import { FaqComponent } from './views/info/faq/faq.component';
import { PrivacyComponent } from './views/info/privacy/privacy.component';
import { TncComponent } from './views/info/tnc/tnc.component';

const routes: Routes = [{
  path: '',
  component: LayoutComponent,
  children: [
    { path: 'timwe', loadChildren: () => import('./views/timwe/timwe.module').then(m => m.TimweModule) },
    {
      path: Config.TIMWE + '/unsubscribe', loadChildren: () => import('./views/unsubscribe/timwe/timwe.module').then(m => m.TimweModule)
    },
    {
      path: 'redirect/subscribe/' + Config.TIMWE , loadChildren: () =>
                       import('./views/redirect/timwe/timwe.module').then(m => m.TimweModule)
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
      path        : '',
      pathMatch   : 'full',
      component   : HomeComponent
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
