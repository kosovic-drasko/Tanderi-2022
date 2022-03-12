import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ViewVrednovanjeComponent } from '../list/view-vrednovanje.component';
import { ViewVrednovanjeDetailComponent } from '../detail/view-vrednovanje-detail.component';
import { ViewVrednovanjeUpdateComponent } from '../update/view-vrednovanje-update.component';
import { ViewVrednovanjeRoutingResolveService } from './view-vrednovanje-routing-resolve.service';

const viewVrednovanjeRoute: Routes = [
  {
    path: '',
    component: ViewVrednovanjeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ViewVrednovanjeDetailComponent,
    resolve: {
      viewVrednovanje: ViewVrednovanjeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ViewVrednovanjeUpdateComponent,
    resolve: {
      viewVrednovanje: ViewVrednovanjeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ViewVrednovanjeUpdateComponent,
    resolve: {
      viewVrednovanje: ViewVrednovanjeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(viewVrednovanjeRoute)],
  exports: [RouterModule],
})
export class ViewVrednovanjeRoutingModule {}
