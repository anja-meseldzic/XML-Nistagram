import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MicroservicesRoutingModule } from './microservices-routing.module';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {MatSelectModule} from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatRadioModule } from '@angular/material/radio';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatTableModule} from '@angular/material/table';
import { MatDividerModule } from '@angular/material/divider'
import { RegistrationComponent } from './auth-service/registration/registration.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { PersonalInfoEditComponent } from './auth-service/personal-info-edit/personal-info-edit.component';
import { LoginComponent } from './auth-service/login/login.component';
import { FeedComponent } from './components/feed/feed.component';
import { ProfileComponent } from './components/profile/profile.component';
import { NgImageSliderModule } from 'ng-image-slider';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import {MatTabsModule} from '@angular/material/tabs'; 
import { FlexLayoutModule } from "@angular/flex-layout";
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { CreateStoryComponent } from './create-story/create-story.component';
import { CreateAlbumComponent } from './create-album/create-album.component';
import { MatListModule } from '@angular/material/list';
import { FollowerRequestDialogComponent } from './components/follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from './components/followers-dialog/followers-dialog.component'; 


@NgModule({
  declarations: [LandingPageComponent, RegistrationComponent, FeedComponent, ProfileComponent, PostDetailsComponent, SearchResultsComponent, CreatePostComponent, PersonalInfoEditComponent, LoginComponent, CreateStoryComponent, CreateAlbumComponent, FollowerRequestDialogComponent, FollowersDialogComponent],
  imports: [
    CommonModule,
    MicroservicesRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    FormsModule,
    MatSelectModule,
    NgImageSliderModule,
    MatDividerModule,
    MatTabsModule,
    FlexLayoutModule,
    MatListModule
  ]
})
export class MicroservicesModule { }
