import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TimweRoutingModule } from './timwe-routing.module';
import { TimweComponent } from './timwe.component';


@NgModule({
  declarations: [TimweComponent],
  imports: [
    CommonModule,
    TimweRoutingModule
  ]
})
export class TimweModule { }
