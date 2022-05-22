import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EventStudentService } from '../service/event-student.service';
import { IEventStudent, EventStudent } from '../event-student.model';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';

import { EventStudentUpdateComponent } from './event-student-update.component';

describe('EventStudent Management Update Component', () => {
  let comp: EventStudentUpdateComponent;
  let fixture: ComponentFixture<EventStudentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eventStudentService: EventStudentService;
  let studentService: StudentService;
  let eventService: EventService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EventStudentUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EventStudentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EventStudentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eventStudentService = TestBed.inject(EventStudentService);
    studentService = TestBed.inject(StudentService);
    eventService = TestBed.inject(EventService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call student query and add missing value', () => {
      const eventStudent: IEventStudent = { id: 456 };
      const student: IStudent = { id: 48162 };
      eventStudent.student = student;

      const studentCollection: IStudent[] = [{ id: 36578 }];
      jest.spyOn(studentService, 'query').mockReturnValue(of(new HttpResponse({ body: studentCollection })));
      const expectedCollection: IStudent[] = [student, ...studentCollection];
      jest.spyOn(studentService, 'addStudentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventStudent });
      comp.ngOnInit();

      expect(studentService.query).toHaveBeenCalled();
      expect(studentService.addStudentToCollectionIfMissing).toHaveBeenCalledWith(studentCollection, student);
      expect(comp.studentsCollection).toEqual(expectedCollection);
    });

    it('Should call event query and add missing value', () => {
      const eventStudent: IEventStudent = { id: 456 };
      const event: IEvent = { id: 87283 };
      eventStudent.event = event;

      const eventCollection: IEvent[] = [{ id: 82399 }];
      jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
      const expectedCollection: IEvent[] = [event, ...eventCollection];
      jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eventStudent });
      comp.ngOnInit();

      expect(eventService.query).toHaveBeenCalled();
      expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(eventCollection, event);
      expect(comp.eventsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eventStudent: IEventStudent = { id: 456 };
      const student: IStudent = { id: 25511 };
      eventStudent.student = student;
      const event: IEvent = { id: 68217 };
      eventStudent.event = event;

      activatedRoute.data = of({ eventStudent });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(eventStudent));
      expect(comp.studentsCollection).toContain(student);
      expect(comp.eventsCollection).toContain(event);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EventStudent>>();
      const eventStudent = { id: 123 };
      jest.spyOn(eventStudentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventStudent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventStudent }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(eventStudentService.update).toHaveBeenCalledWith(eventStudent);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EventStudent>>();
      const eventStudent = new EventStudent();
      jest.spyOn(eventStudentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventStudent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eventStudent }));
      saveSubject.complete();

      // THEN
      expect(eventStudentService.create).toHaveBeenCalledWith(eventStudent);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EventStudent>>();
      const eventStudent = { id: 123 };
      jest.spyOn(eventStudentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eventStudent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eventStudentService.update).toHaveBeenCalledWith(eventStudent);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackStudentById', () => {
      it('Should return tracked Student primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStudentById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEventById', () => {
      it('Should return tracked Event primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEventById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
