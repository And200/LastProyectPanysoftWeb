import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderPlacedComponent } from '../list/order-placed.component';
import { OrderPlacedDetailComponent } from '../detail/order-placed-detail.component';
import { OrderPlacedUpdateComponent } from '../update/order-placed-update.component';
import { OrderPlacedRoutingResolveService } from './order-placed-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const orderPlacedRoute: Routes = [
  {
    path: '',
    component: OrderPlacedComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderPlacedDetailComponent,
    resolve: {
      orderPlaced: OrderPlacedRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderPlacedUpdateComponent,
    resolve: {
      orderPlaced: OrderPlacedRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderPlacedUpdateComponent,
    resolve: {
      orderPlaced: OrderPlacedRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orderPlacedRoute)],
  exports: [RouterModule],
})
export class OrderPlacedRoutingModule {}
