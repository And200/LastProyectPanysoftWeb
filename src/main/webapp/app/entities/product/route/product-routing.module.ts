import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductComponent } from '../list/product.component';
import { ProductDetailComponent } from '../detail/product-detail.component';
import { ProductUpdateComponent } from '../update/product-update.component';
import { ProductRoutingResolveService } from './product-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const productRoute: Routes = [
  {
    path: '',
    component: ProductComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN, Authority.BAKER, Authority.CASHIER, Authority.CLIENT, Authority.WAITER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductDetailComponent,
    resolve: {
      product: ProductRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.BAKER, Authority.CASHIER, Authority.CLIENT, Authority.WAITER, Authority.CASHIER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductUpdateComponent,
    resolve: {
      product: ProductRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.BAKER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductUpdateComponent,
    resolve: {
      product: ProductRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.BAKER],
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productRoute)],
  exports: [RouterModule],
})
export class ProductRoutingModule {}
