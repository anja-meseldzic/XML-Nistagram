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
import { VerifyAccountComponent } from './microservices/components/verify-account/verify-account.component';
import { VerificationRequestsComponent } from './microservices/components/verification-requests/verification-requests.component';
import { LikedDataComponent } from './microservices/components/liked-data/liked-data.component';
import { DislikedDataComponent } from './microservices/components/disliked-data/disliked-data.component';
import { NotificationSettingsComponent } from './microservices/components/notification-settings/notification-settings.component';
import { NotificationsComponent } from './microservices/components/notifications/notifications.component';
import { InappropriateContentComponent } from './microservices/components/inappropriate-content/inappropriate-content.component';
import { RegisterAgentComponent } from './microservices/auth-service/register-agent/register-agent.component';
import { AgentRequestsComponent } from './microservices/components/agent-requests/agent-requests.component';
import { RegisterAgentAdminComponent } from './microservices/components/register-agent-admin/register-agent-admin.component';
import {ChatComponent} from './microservices/chat/chat.component';
import { NewCampaignComponent } from './microservices/components/new-campaign/new-campaign.component';
import { CampaignsComponent } from './microservices/components/campaigns/campaigns.component';

const routes: Routes = [
  {path: 'registration', component: RegistrationComponent, canActivate: [RouteGuardService], data: {expectedRoles: []}},
  {path: 'registration-agent', component: RegisterAgentComponent, canActivate: [RouteGuardService], data: {expectedRoles: []}},
  {path: 'registration-agent-admin', component: RegisterAgentAdminComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['ADMIN']}},
  {path: 'personal-edit', component: PersonalInfoEditComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'profile-config', component: ProfileConfigComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'login', component: LoginComponent, canActivate: [RouteGuardService], data: {expectedRoles: []}},
  {path: 'feed', component: FeedComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'chat', component: ChatComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'campaign/add', component: NewCampaignComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['AGENT']}},
  {path: 'campaign', component: CampaignsComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['AGENT']}},
  {path: 'post/:id', component: PostDetailsComponent},
  {path: 'post', component: SearchResultsComponent},
  {path: 'profile/:username', component: ProfileComponent },
  {path: 'unauthorized', component: UnauthorizedPageComponent},
  {path: 'verify-profile', component: VerifyAccountComponent},
  {path: 'verification-requests', component: VerificationRequestsComponent},
  {path: 'inappropriate-content', component: InappropriateContentComponent},
  {path: 'agent-requests', component: AgentRequestsComponent},
  {path: 'likedData', component: LikedDataComponent},
  {path: 'dislikedData', component: DislikedDataComponent},
  {path: 'settings/notifications', component: NotificationSettingsComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: 'notifications', component: NotificationsComponent, canActivate: [RouteGuardService], data: {expectedRoles: ['USER', 'AGENT']}},
  {path: '', loadChildren: () => import('./microservices/microservices.module').then(mod => mod.MicroservicesModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
