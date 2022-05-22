import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEventStudent, getEventStudentIdentifier } from '../event-student.model';

export type EntityResponseType = HttpResponse<IEventStudent>;
export type EntityArrayResponseType = HttpResponse<IEventStudent[]>;

@Injectable({ providedIn: 'root' })
export class EventStudentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/event-students');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(eventStudent: IEventStudent): Observable<EntityResponseType> {
    return this.http.post<IEventStudent>(this.resourceUrl, eventStudent, { observe: 'response' });
  }

  update(eventStudent: IEventStudent): Observable<EntityResponseType> {
    return this.http.put<IEventStudent>(`${this.resourceUrl}/${getEventStudentIdentifier(eventStudent) as number}`, eventStudent, {
      observe: 'response',
    });
  }

  partialUpdate(eventStudent: IEventStudent): Observable<EntityResponseType> {
    return this.http.patch<IEventStudent>(`${this.resourceUrl}/${getEventStudentIdentifier(eventStudent) as number}`, eventStudent, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventStudent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventStudent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEventStudentToCollectionIfMissing(
    eventStudentCollection: IEventStudent[],
    ...eventStudentsToCheck: (IEventStudent | null | undefined)[]
  ): IEventStudent[] {
    const eventStudents: IEventStudent[] = eventStudentsToCheck.filter(isPresent);
    if (eventStudents.length > 0) {
      const eventStudentCollectionIdentifiers = eventStudentCollection.map(
        eventStudentItem => getEventStudentIdentifier(eventStudentItem)!
      );
      const eventStudentsToAdd = eventStudents.filter(eventStudentItem => {
        const eventStudentIdentifier = getEventStudentIdentifier(eventStudentItem);
        if (eventStudentIdentifier == null || eventStudentCollectionIdentifiers.includes(eventStudentIdentifier)) {
          return false;
        }
        eventStudentCollectionIdentifiers.push(eventStudentIdentifier);
        return true;
      });
      return [...eventStudentsToAdd, ...eventStudentCollection];
    }
    return eventStudentCollection;
  }
}
