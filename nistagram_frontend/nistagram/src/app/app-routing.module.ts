import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LandingPageComponent} from './microservices/landing-page/landing-page.component';
import {RegistrationComponent} from './microservices/auth-service/registration/registration.component';
import {PersonalInfoEditComponent} from './microservices/auth-service/personal-info-edit/personal-info-edit.component';
import {LoginComponent} from './microservices/auth-service/login/login.component';

const routes: Routes = [
  {path: '', loadChildren: () => import('./microservices/microservices.module').then(mod => mod.MicroservicesModule)},
  // {path: '', component: LandingPageComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'personal-edit', component: PersonalInfoEditComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
