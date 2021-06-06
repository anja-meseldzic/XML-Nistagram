import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CreateAlbumComponent } from './create-album/create-album.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { CreateStoryComponent } from './create-story/create-story.component';
import { RouteGuardService } from './guards/route-guard.service';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { ReactionsListComponent } from './reactions-list/reactions-list.component';

const routes: Routes = [
  {path: '', component: LandingPageComponent, canActivate: [RouteGuardService], data: {expectedRoles: []}},
  {path: 'create-post', component: CreatePostComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'create-story', component: CreateStoryComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'create-album', component: CreateAlbumComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'reactions/:id', component: ReactionsListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MicroservicesRoutingModule { }
