import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'student',
        data: { pageTitle: 'schoolEventApp.student.home.title' },
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
      },
      {
        path: 'event',
        data: { pageTitle: 'schoolEventApp.event.home.title' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'event-student',
        data: { pageTitle: 'schoolEventApp.eventStudent.home.title' },
        loadChildren: () => import('./event-student/event-student.module').then(m => m.EventStudentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
