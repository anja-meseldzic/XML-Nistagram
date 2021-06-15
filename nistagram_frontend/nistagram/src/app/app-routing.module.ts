import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegistrationComponent} from './microservices/auth-service/registration/registration.component';
import {PersonalInfoEditComponent} from './microservices/auth-service/personal-info-edit/personal-info-edit.component';
import {LoginComponent} from './microservices/auth-service/login/login.component';
import { FeedComponent } from './microservices/components/feed/feed.component';
import { PostDetailsComponent } from './microservices/components/post-details/post-details.component';
import { ProfileComponent } from './microservices/components/profile/profile.component';
import { SearchResultsComponent } from './microservices/components/search-results/search-results.component';
import {UnauthorizedPageComponent} from './microservices/auth-service/unauthorized-page/unauthorized-page.component';
import { RouteGuardService } from './microservices/guards/route-guard.service';
import {ProfileConfigComponent} from './microservices/profile-config/profile-config.component';

const routes: Routes = [
  {path: 'registration', component: RegistrationComponent, canActivate: [RouteGuardService], data: {expectedRoles: []}},
  {path: 'personal-edit', component: PersonalInfoEditComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'profile-config', component: ProfileConfigComponent},
  {path: 'login', component: LoginComponent, canActivate: [RouteGuardService], data: {expectedRoles: []}},
  {path: 'feed', component: FeedComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'post/:id', component: PostDetailsComponent},
  {path: 'post', component: SearchResultsComponent},
  {path: 'profile/:username', component: ProfileComponent },
  {path: 'unauthorized', component: UnauthorizedPageComponent},
  {path: '', loadChildren: () => import('./microservices/microservices.module').then(mod => mod.MicroservicesModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
