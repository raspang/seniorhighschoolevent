export interface IStudent {
  id?: number;
  firstName?: string;
  middleName?: string | null;
  lastName?: string;
  gender?: string;
  lrn?: string | null;
  yearLevel?: string | null;
  strand?: string | null;
  section?: string | null;
  institutionalEmail?: string | null;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public firstName?: string,
    public middleName?: string | null,
    public lastName?: string,
    public gender?: string,
    public lrn?: string | null,
    public yearLevel?: string | null,
    public strand?: string | null,
    public section?: string | null,
    public institutionalEmail?: string | null
  ) {}
}

export function getStudentIdentifier(student: IStudent): number | undefined {
  return student.id;
}
