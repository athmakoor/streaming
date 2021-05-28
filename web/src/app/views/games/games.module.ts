import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GamesRoutingModule } from './games-routing.module';
import { GamesComponent } from './games.component';
import { GameCategoryListComponent } from './game-category-list/game-category-list.component';
import { MaterialModule } from 'src/app/shared/material.module';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [GamesComponent, GameCategoryListComponent],
  imports: [
    CommonModule,
    GamesRoutingModule,
    SharedModule,
    MaterialModule
  ]
})
export class GamesModule { }
