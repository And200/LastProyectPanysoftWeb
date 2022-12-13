import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailOrderComponent } from '../list/detail-order.component';
import { DetailOrderDetailComponent } from '../detail/detail-order-detail.component';
import { DetailOrderUpdateComponent } from '../update/detail-order-update.component';
import { DetailOrderRoutingResolveService } from './detail-order-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const detailOrderRoute: Routes = [
  {
    path: '',
    component: DetailOrderComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailOrderDetailComponent,
    resolve: {
      detailOrder: DetailOrderRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailOrderUpdateComponent,
    resolve: {
      detailOrder: DetailOrderRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailOrderUpdateComponent,
    resolve: {
      detailOrder: DetailOrderRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailOrderRoute)],
  exports: [RouterModule],
})
export class DetailOrderRoutingModule {}
