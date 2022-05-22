import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventStudentComponent } from '../list/event-student.component';
import { EventStudentDetailComponent } from '../detail/event-student-detail.component';
import { EventStudentUpdateComponent } from '../update/event-student-update.component';
import { EventStudentRoutingResolveService } from './event-student-routing-resolve.service';

const eventStudentRoute: Routes = [
  {
    path: '',
    component: EventStudentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventStudentDetailComponent,
    resolve: {
      eventStudent: EventStudentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventStudentUpdateComponent,
    resolve: {
      eventStudent: EventStudentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventStudentUpdateComponent,
    resolve: {
      eventStudent: EventStudentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventStudentRoute)],
  exports: [RouterModule],
})
export class EventStudentRoutingModule {}
