import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TimweComponent } from './timwe.component';

const routes: Routes = [{ path: '', component: TimweComponent }, { path: ':locale/subscribe', loadChildren: () => import('./subscribe/subscribe.module').then(m => m.SubscribeModule) }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TimweRoutingModule { }
