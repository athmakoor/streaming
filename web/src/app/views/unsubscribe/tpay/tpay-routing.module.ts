import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TpayComponent } from './tpay.component';

const routes: Routes = [{ path: '', component: TpayComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TpayRoutingModule { }
