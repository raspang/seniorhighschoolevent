import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EventStudentDetailComponent } from './event-student-detail.component';

describe('EventStudent Management Detail Component', () => {
  let comp: EventStudentDetailComponent;
  let fixture: ComponentFixture<EventStudentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EventStudentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ eventStudent: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EventStudentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EventStudentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load eventStudent on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.eventStudent).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
