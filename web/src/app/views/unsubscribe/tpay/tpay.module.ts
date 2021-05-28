import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TpayRoutingModule } from './tpay-routing.module';
import { TpayComponent } from './tpay.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';


@NgModule({
  declarations: [TpayComponent],
  imports: [
    CommonModule,
    TpayRoutingModule,
    MatCardModule,
    MatButtonModule
  ]
})
export class TpayModule { }
