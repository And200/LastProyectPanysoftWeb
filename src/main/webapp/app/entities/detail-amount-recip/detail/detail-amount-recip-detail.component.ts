import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetailAmountRecip } from '../detail-amount-recip.model';

@Component({
  selector: 'panysoft-detail-amount-recip-detail',
  templateUrl: './detail-amount-recip-detail.component.html',
})
export class DetailAmountRecipDetailComponent implements OnInit {
  detailAmountRecip: IDetailAmountRecip | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ detailAmountRecip }) => {
      this.detailAmountRecip = detailAmountRecip;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
