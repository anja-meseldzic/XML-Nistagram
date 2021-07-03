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
import { UnauthorizedPageComponent } from './auth-service/unauthorized-page/unauthorized-page.component';
import { FollowerRequestDialogComponent } from './components/follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from './components/followers-dialog/followers-dialog.component';
import { ReactionsListComponent } from './reactions-list/reactions-list.component';
import { CloseFriendsComponent } from './components/close-friends/close-friends.component';
import { CollectionDialogComponent } from './components/collection-dialog/collection-dialog.component';
import { RatingsDialogComponent } from './components/ratings-dialog/ratings-dialog.component';
import { LikedDataComponent } from './components/liked-data/liked-data.component';
import { DislikedDataComponent } from './components/disliked-data/disliked-data.component';
import { ReportDialogComponent } from './components/report-dialog/report-dialog.component';
import { VerifyAccountComponent } from './components/verify-account/verify-account.component';
import { VerificationRequestsComponent } from './components/verification-requests/verification-requests.component';
import { ProfileConfigComponent } from './profile-config/profile-config.component';
import { NotificationSettingsComponent } from './components/notification-settings/notification-settings.component';
import { NotificationsComponent } from './components/notifications/notifications.component';
import { InappropriateContentComponent } from './components/inappropriate-content/inappropriate-content.component';
import { RegisterAgentComponent } from './auth-service/register-agent/register-agent.component';
import { AgentRequestsComponent } from './components/agent-requests/agent-requests.component';
import { RegisterAgentAdminComponent } from './components/register-agent-admin/register-agent-admin.component';

@NgModule({
  declarations: [LandingPageComponent, RegistrationComponent, FeedComponent, ProfileComponent, PostDetailsComponent, SearchResultsComponent, CreatePostComponent, PersonalInfoEditComponent, LoginComponent, CreateStoryComponent, CreateAlbumComponent, FollowerRequestDialogComponent, FollowersDialogComponent,UnauthorizedPageComponent, ReactionsListComponent, CloseFriendsComponent, CollectionDialogComponent, ProfileConfigComponent, VerifyAccountComponent, VerificationRequestsComponent, RatingsDialogComponent, LikedDataComponent, DislikedDataComponent, ReportDialogComponent, NotificationSettingsComponent, NotificationsComponent, InappropriateContentComponent, RegisterAgentComponent, AgentRequestsComponent, RegisterAgentAdminComponent],
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
