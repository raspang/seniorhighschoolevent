import dayjs from 'dayjs/esm';

export interface IEvent {
  id?: number;
  dateEvent?: dayjs.Dayjs | null;
  nameEvent?: string;
}

export class Event implements IEvent {
  constructor(public id?: number, public dateEvent?: dayjs.Dayjs | null, public nameEvent?: string) {}
}

export function getEventIdentifier(event: IEvent): number | undefined {
  return event.id;
}
