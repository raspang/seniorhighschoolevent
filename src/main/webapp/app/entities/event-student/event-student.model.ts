import { IStudent } from 'app/entities/student/student.model';
import { IEvent } from 'app/entities/event/event.model';

export interface IEventStudent {
  id?: number;
  student?: IStudent | null;
  event?: IEvent | null;
}

export class EventStudent implements IEventStudent {
  constructor(public id?: number, public student?: IStudent | null, public event?: IEvent | null) {}
}

export function getEventStudentIdentifier(eventStudent: IEventStudent): number | undefined {
  return eventStudent.id;
}
