import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegistrationComponent} from "./registration/registration.component";
import {GuardService} from "./guards/guard.service";
import {LoginComponent} from "./login/login.component";
import {LandingPageComponent} from "./landing-page/landing-page.component";

const routes: Routes = [
  {path: '', component: LandingPageComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
