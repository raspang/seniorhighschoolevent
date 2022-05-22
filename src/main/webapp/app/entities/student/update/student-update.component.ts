import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IStudent, Student } from '../student.model';
import { StudentService } from '../service/student.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    middleName: [],
    lastName: [null, [Validators.required]],
    gender: [null, [Validators.required]],
    lrn: [],
    yearLevel: [],
    strand: [],
    section: [],
    institutionalEmail: [],
  });

  constructor(protected studentService: StudentService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.updateForm(student);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
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

  protected updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      firstName: student.firstName,
      middleName: student.middleName,
      lastName: student.lastName,
      gender: student.gender,
      lrn: student.lrn,
      yearLevel: student.yearLevel,
      strand: student.strand,
      section: student.section,
      institutionalEmail: student.institutionalEmail,
    });
  }

  protected createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      lrn: this.editForm.get(['lrn'])!.value,
      yearLevel: this.editForm.get(['yearLevel'])!.value,
      strand: this.editForm.get(['strand'])!.value,
      section: this.editForm.get(['section'])!.value,
      institutionalEmail: this.editForm.get(['institutionalEmail'])!.value,
    };
  }
}
