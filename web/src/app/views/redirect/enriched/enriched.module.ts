import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EnrichedRoutingModule } from './enriched-routing.module';
import { EnrichedComponent } from './enriched.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';


@NgModule({
  declarations: [EnrichedComponent],
  imports: [
    CommonModule,
    EnrichedRoutingModule,
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
  ]
})
export class EnrichedModule { }
