import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEventStudent, EventStudent } from '../event-student.model';
import { EventStudentService } from '../service/event-student.service';

@Injectable({ providedIn: 'root' })
export class EventStudentRoutingResolveService implements Resolve<IEventStudent> {
  constructor(protected service: EventStudentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventStudent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((eventStudent: HttpResponse<EventStudent>) => {
          if (eventStudent.body) {
            return of(eventStudent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventStudent());
  }
}
