import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EnrichedComponent } from './enriched.component';

const routes: Routes = [{ path: '', component: EnrichedComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EnrichedRoutingModule { }
