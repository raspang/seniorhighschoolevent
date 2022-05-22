import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventStudent } from '../event-student.model';

@Component({
  selector: 'jhi-event-student-detail',
  templateUrl: './event-student-detail.component.html',
})
export class EventStudentDetailComponent implements OnInit {
  eventStudent: IEventStudent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventStudent }) => {
      this.eventStudent = eventStudent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
