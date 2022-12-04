import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DetailSaleComponent } from '../list/detail-sale.component';
import { DetailSaleDetailComponent } from '../detail/detail-sale-detail.component';
import { DetailSaleUpdateComponent } from '../update/detail-sale-update.component';
import { DetailSaleRoutingResolveService } from './detail-sale-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const detailSaleRoute: Routes = [
  {
    path: '',
    component: DetailSaleComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN, Authority.CASHIER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DetailSaleDetailComponent,
    resolve: {
      detailSale: DetailSaleRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.CASHIER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DetailSaleUpdateComponent,
    resolve: {
      detailSale: DetailSaleRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.CASHIER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DetailSaleUpdateComponent,
    resolve: {
      detailSale: DetailSaleRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.CASHIER],
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(detailSaleRoute)],
  exports: [RouterModule],
})
export class DetailSaleRoutingModule {}
