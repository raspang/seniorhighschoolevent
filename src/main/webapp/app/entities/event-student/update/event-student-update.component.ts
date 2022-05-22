import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEventStudent, EventStudent } from '../event-student.model';
import { EventStudentService } from '../service/event-student.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';

@Component({
  selector: 'jhi-event-student-update',
  templateUrl: './event-student-update.component.html',
})
export class EventStudentUpdateComponent implements OnInit {
  isSaving = false;

  studentsCollection: IStudent[] = [];
  eventsCollection: IEvent[] = [];

  editForm = this.fb.group({
    id: [],
    student: [],
    event: [],
  });

  constructor(
    protected eventStudentService: EventStudentService,
    protected studentService: StudentService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventStudent }) => {
      this.updateForm(eventStudent);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventStudent = this.createFromForm();
    if (eventStudent.id !== undefined) {
      this.subscribeToSaveResponse(this.eventStudentService.update(eventStudent));
    } else {
      this.subscribeToSaveResponse(this.eventStudentService.create(eventStudent));
    }
  }

  trackStudentById(_index: number, item: IStudent): number {
    return item.id!;
  }

  trackEventById(_index: number, item: IEvent): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventStudent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(eventStudent: IEventStudent): void {
    this.editForm.patchValue({
      id: eventStudent.id,
      student: eventStudent.student,
      event: eventStudent.event,
    });

    this.studentsCollection = this.studentService.addStudentToCollectionIfMissing(this.studentsCollection, eventStudent.student);
    this.eventsCollection = this.eventService.addEventToCollectionIfMissing(this.eventsCollection, eventStudent.event);
  }

  protected loadRelationshipsOptions(): void {
    this.studentService
      .query({ filter: 'eventstudent-is-null' })
      .pipe(map((res: HttpResponse<IStudent[]>) => res.body ?? []))
      .pipe(
        map((students: IStudent[]) => this.studentService.addStudentToCollectionIfMissing(students, this.editForm.get('student')!.value))
      )
      .subscribe((students: IStudent[]) => (this.studentsCollection = students));

    this.eventService
      .query({ filter: 'eventstudent-is-null' })
      .pipe(map((res: HttpResponse<IEvent[]>) => res.body ?? []))
      .pipe(map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing(events, this.editForm.get('event')!.value)))
      .subscribe((events: IEvent[]) => (this.eventsCollection = events));
  }

  protected createFromForm(): IEventStudent {
    return {
      ...new EventStudent(),
      id: this.editForm.get(['id'])!.value,
      student: this.editForm.get(['student'])!.value,
      event: this.editForm.get(['event'])!.value,
    };
  }
}
