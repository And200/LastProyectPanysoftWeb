import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PresentationComponent } from '../list/presentation.component';
import { PresentationDetailComponent } from '../detail/presentation-detail.component';
import { PresentationUpdateComponent } from '../update/presentation-update.component';
import { PresentationRoutingResolveService } from './presentation-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const presentationRoute: Routes = [
  {
    path: '',
    component: PresentationComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN, Authority.BAKER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PresentationDetailComponent,
    resolve: {
      presentation: PresentationRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.BAKER],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PresentationUpdateComponent,
    resolve: {
      presentation: PresentationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PresentationUpdateComponent,
    resolve: {
      presentation: PresentationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(presentationRoute)],
  exports: [RouterModule],
})
export class PresentationRoutingModule {}
