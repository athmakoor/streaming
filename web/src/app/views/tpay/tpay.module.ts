import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TpayRoutingModule } from './tpay-routing.module';
import { TpayComponent } from './tpay.component';


@NgModule({
  declarations: [TpayComponent],
  imports: [
    CommonModule,
    TpayRoutingModule
  ]
})
export class TpayModule { }
