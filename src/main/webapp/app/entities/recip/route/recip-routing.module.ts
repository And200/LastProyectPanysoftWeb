import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RecipComponent } from '../list/recip.component';
import { RecipDetailComponent } from '../detail/recip-detail.component';
import { RecipUpdateComponent } from '../update/recip-update.component';
import { RecipRoutingResolveService } from './recip-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const recipRoute: Routes = [
  {
    path: '',
    component: RecipComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RecipDetailComponent,
    resolve: {
      recip: RecipRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RecipUpdateComponent,
    resolve: {
      recip: RecipRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RecipUpdateComponent,
    resolve: {
      recip: RecipRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(recipRoute)],
  exports: [RouterModule],
})
export class RecipRoutingModule {}
