import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TimweRoutingModule } from './timwe-routing.module';
import { TimweComponent } from './timwe.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';


@NgModule({
  declarations: [TimweComponent],
  imports: [
    CommonModule,
    TimweRoutingModule,
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
  ]
})
export class TimweModule { }
