import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

import {EventsListComponent} from "./eventsList/events-list/events-list.component";
import {AddEventComponent} from "./eventsList/add-event/add-event.component";
import {ParticipantComponent} from "./eventsList/participant/participant.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'events', component: EventsListComponent },
  { path: 'add', component: AddEventComponent },
  { path: 'participant', component: ParticipantComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
