import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventStudent } from '../event-student.model';
import { EventStudentService } from '../service/event-student.service';

@Component({
  templateUrl: './event-student-delete-dialog.component.html',
})
export class EventStudentDeleteDialogComponent {
  eventStudent?: IEventStudent;

  constructor(protected eventStudentService: EventStudentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventStudentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
