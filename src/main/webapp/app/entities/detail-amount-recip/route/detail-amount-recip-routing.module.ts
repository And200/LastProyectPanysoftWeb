import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailAmountRecipComponent } from '../list/detail-amount-recip.component';
import { DetailAmountRecipDetailComponent } from '../detail/detail-amount-recip-detail.component';
import { DetailAmountRecipUpdateComponent } from '../update/detail-amount-recip-update.component';
import { DetailAmountRecipRoutingResolveService } from './detail-amount-recip-routing-resolve.service';

const detailAmountRecipRoute: Routes = [
  {
    path: '',
    component: DetailAmountRecipComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailAmountRecipDetailComponent,
    resolve: {
      detailAmountRecip: DetailAmountRecipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailAmountRecipUpdateComponent,
    resolve: {
      detailAmountRecip: DetailAmountRecipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailAmountRecipUpdateComponent,
    resolve: {
      detailAmountRecip: DetailAmountRecipRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailAmountRecipRoute)],
  exports: [RouterModule],
})
export class DetailAmountRecipRoutingModule {}
