import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TpayComponent } from './tpay.component';

const routes: Routes = [
{ path: '', component: TpayComponent },
{ path: ':locale/subscribe', loadChildren: () =>
         import('./subscribe/subscribe.module').then(m => m.SubscribeModule)
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TpayRoutingModule { }
