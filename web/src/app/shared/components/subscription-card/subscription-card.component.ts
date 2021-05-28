import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SubscriptionService } from '../../../services/auth/subscription.service';

@Component({
  selector: 'app-subscription-card',
  templateUrl: './subscription-card.component.html',
  styleUrls: ['./subscription-card.component.css']
})

export class SubscriptionCardComponent implements OnInit {
  @Input() plan: any;

  paramData: any;
  status: any;

  constructor(private route: ActivatedRoute, private readonly subscriptionService: SubscriptionService, private readonly router: Router) {
  }

  ngOnInit(): void {
  }

  onSubscribePlan(): void {
    status = 'sending OTP to your registered number';
    this.route.queryParams.subscribe(qParams => {
      this.paramData = qParams;

      this.subscriptionService.subscribePlan(this.paramData).subscribe(
        data => {
          status = 'OTP has been sent to registered number';

          if (data) {
            this.router.navigate(['/otp'], {
              relativeTo: this.route,
              queryParams: {
                msisdn: this.paramData.msisdn,
                transactionId: this.paramData.tId
              },
              queryParamsHandling: 'merge'
            });
          }
        },
        error => {

        });
    });
  }

}
