import { Component, OnInit } from '@angular/core';
import { Config } from 'src/app/common/constants/config';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    window.location.href = Config.APP_URL + '?provider=' + Config.TIMWE;
  }
}
