import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventStudentComponent } from './list/event-student.component';
import { EventStudentDetailComponent } from './detail/event-student-detail.component';
import { EventStudentUpdateComponent } from './update/event-student-update.component';
import { EventStudentDeleteDialogComponent } from './delete/event-student-delete-dialog.component';
import { EventStudentRoutingModule } from './route/event-student-routing.module';

@NgModule({
  imports: [SharedModule, EventStudentRoutingModule],
  declarations: [EventStudentComponent, EventStudentDetailComponent, EventStudentUpdateComponent, EventStudentDeleteDialogComponent],
  entryComponents: [EventStudentDeleteDialogComponent],
})
export class EventStudentModule {}
