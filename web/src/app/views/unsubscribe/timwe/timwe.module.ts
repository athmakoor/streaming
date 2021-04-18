import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TimweRoutingModule } from './timwe-routing.module';
import { TimweComponent } from './timwe.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';


@NgModule({
  declarations: [TimweComponent],
  imports: [
    CommonModule,
    TimweRoutingModule,
    MatCardModule,
    MatButtonModule
  ]
})
export class TimweModule { }
