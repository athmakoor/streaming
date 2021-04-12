import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TimweComponent } from './timwe.component';

const routes: Routes = [{ path: '', component: TimweComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TimweRoutingModule { }
