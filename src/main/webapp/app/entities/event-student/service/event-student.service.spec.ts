import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEventStudent, EventStudent } from '../event-student.model';

import { EventStudentService } from './event-student.service';

describe('EventStudent Service', () => {
  let service: EventStudentService;
  let httpMock: HttpTestingController;
  let elemDefault: IEventStudent;
  let expectedResult: IEventStudent | IEventStudent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EventStudentService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a EventStudent', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new EventStudent()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EventStudent', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EventStudent', () => {
      const patchObject = Object.assign({}, new EventStudent());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EventStudent', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a EventStudent', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addEventStudentToCollectionIfMissing', () => {
      it('should add a EventStudent to an empty array', () => {
        const eventStudent: IEventStudent = { id: 123 };
        expectedResult = service.addEventStudentToCollectionIfMissing([], eventStudent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventStudent);
      });

      it('should not add a EventStudent to an array that contains it', () => {
        const eventStudent: IEventStudent = { id: 123 };
        const eventStudentCollection: IEventStudent[] = [
          {
            ...eventStudent,
          },
          { id: 456 },
        ];
        expectedResult = service.addEventStudentToCollectionIfMissing(eventStudentCollection, eventStudent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EventStudent to an array that doesn't contain it", () => {
        const eventStudent: IEventStudent = { id: 123 };
        const eventStudentCollection: IEventStudent[] = [{ id: 456 }];
        expectedResult = service.addEventStudentToCollectionIfMissing(eventStudentCollection, eventStudent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventStudent);
      });

      it('should add only unique EventStudent to an array', () => {
        const eventStudentArray: IEventStudent[] = [{ id: 123 }, { id: 456 }, { id: 15800 }];
        const eventStudentCollection: IEventStudent[] = [{ id: 123 }];
        expectedResult = service.addEventStudentToCollectionIfMissing(eventStudentCollection, ...eventStudentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const eventStudent: IEventStudent = { id: 123 };
        const eventStudent2: IEventStudent = { id: 456 };
        expectedResult = service.addEventStudentToCollectionIfMissing([], eventStudent, eventStudent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(eventStudent);
        expect(expectedResult).toContain(eventStudent2);
      });

      it('should accept null and undefined values', () => {
        const eventStudent: IEventStudent = { id: 123 };
        expectedResult = service.addEventStudentToCollectionIfMissing([], null, eventStudent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(eventStudent);
      });

      it('should return initial array if no EventStudent is added', () => {
        const eventStudentCollection: IEventStudent[] = [{ id: 123 }];
        expectedResult = service.addEventStudentToCollectionIfMissing(eventStudentCollection, undefined, null);
        expect(expectedResult).toEqual(eventStudentCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
