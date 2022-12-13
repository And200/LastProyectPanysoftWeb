import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MeasureUnitComponent } from '../list/measure-unit.component';
import { MeasureUnitDetailComponent } from '../detail/measure-unit-detail.component';
import { MeasureUnitUpdateComponent } from '../update/measure-unit-update.component';
import { MeasureUnitRoutingResolveService } from './measure-unit-routing-resolve.service';
import { Authority } from '../../../config/authority.constants';

const measureUnitRoute: Routes = [
  {
    path: '',
    component: MeasureUnitComponent,
    data: {
      defaultSort: 'id,asc',
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MeasureUnitDetailComponent,
    resolve: {
      measureUnit: MeasureUnitRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MeasureUnitUpdateComponent,
    resolve: {
      measureUnit: MeasureUnitRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MeasureUnitUpdateComponent,
    resolve: {
      measureUnit: MeasureUnitRoutingResolveService,
    },
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(measureUnitRoute)],
  exports: [RouterModule],
})
export class MeasureUnitRoutingModule {}
