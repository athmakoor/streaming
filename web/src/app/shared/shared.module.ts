import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GameCardComponent } from './components/game-card/game-card.component';
import { SubscriptionCardComponent } from './components/Subscription-card/Subscription-card.component';
import { HeadingComponent } from './heading/heading.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs';
import {MatDividerModule} from '@angular/material/divider';

const DECLARATIONS = [
  HeadingComponent,
  GameCardComponent,
  SubscriptionCardComponent
];

const IMPORTS = [
  FormsModule,
  ReactiveFormsModule,
  MatGridListModule,
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatToolbarModule,
  MatTabsModule,
  MatDividerModule
];

@NgModule({
  declarations: [
    DECLARATIONS
  ],
  imports: [
    CommonModule,
    IMPORTS
  ],
  exports: [
    DECLARATIONS,
    IMPORTS
  ]
})

export class SharedModule { }
