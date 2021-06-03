import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RegistrationComponent} from './microservices/auth-service/registration/registration.component';
import { FeedComponent } from './microservices/components/feed/feed.component';
import { PostDetailsComponent } from './microservices/components/post-details/post-details.component';
import { ProfileComponent } from './microservices/components/profile/profile.component';
import { SearchResultsComponent } from './microservices/components/search-results/search-results.component';

const routes: Routes = [
  {path: 'registration', component: RegistrationComponent},
  {path: 'feed', component: FeedComponent},
  {path: 'post/:id', component: PostDetailsComponent},
  {path: 'post', component: SearchResultsComponent},
  {path: 'profile/:username', component: ProfileComponent},
  {path: '', loadChildren: () => import('./microservices/microservices.module').then(mod => mod.MicroservicesModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
