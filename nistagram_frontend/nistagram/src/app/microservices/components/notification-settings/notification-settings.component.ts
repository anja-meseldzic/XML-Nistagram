import { Component, OnInit } from '@angular/core';
import { SettingsPerFollow } from '../../model/settings-per-follow';
import { SettingsPerProfile } from '../../model/settings-per-profile';
import { NotificationService } from '../../notification-service/notification.service';

@Component({
  selector: 'app-notification-settings',
  templateUrl: './notification-settings.component.html',
  styleUrls: ['./notification-settings.component.css']
})
export class NotificationSettingsComponent implements OnInit {

  constructor(private notificationService : NotificationService) { }

  public settingsPerProfile : SettingsPerProfile = null;
  public settingsPerFollow : SettingsPerFollow[] = [];

  displayedColumns = ["Profile", "Message", "Post", "Story", "Comment", "Rating"];

  ngOnInit(): void {
    this.notificationService.getForProfile().subscribe(
      data => { this.settingsPerProfile = data;
                this.notificationService.getForFollow().subscribe(
                  data2 => { this.settingsPerFollow = data2; this.order() }
                ) }
    )
  }

  toggleMessage(settings : SettingsPerFollow) {
    settings.notifyOnMessage = !settings.notifyOnMessage;
    this.update(settings)
  }

  togglePost(settings : SettingsPerFollow) {
    settings.notifyOnPost = !settings.notifyOnPost;
    this.update(settings)
  }

  toggleStory(settings : SettingsPerFollow) {
    settings.notifyOnStory = !settings.notifyOnStory
    this.update(settings)
  }

  toggleComment(settings : SettingsPerFollow) {
    settings.notifyOnComment = !settings.notifyOnComment
    this.update(settings)
  }

  toggleRating(settings : SettingsPerFollow) {
    settings.notifyOnRating = !settings.notifyOnRating
    this.update(settings)
  }

  toggleFollow() {
    this.settingsPerProfile.notifyOnFollw = !this.settingsPerProfile.notifyOnFollw
    this.updateGeneral(this.settingsPerProfile)
  }

  toggleAccepted() {
    this.settingsPerProfile.notifyOnAcceptedFollowRequest = !this.settingsPerProfile.notifyOnAcceptedFollowRequest
    this.updateGeneral(this.settingsPerProfile)
  }

  toggleMessageGeneral() {
    this.settingsPerProfile.notifyOnNonFollowedMessage = !this.settingsPerProfile.notifyOnNonFollowedMessage
    this.updateGeneral(this.settingsPerProfile)
  }

  toggleCommentGeneral() {
    this.settingsPerProfile.notifyOnNonFollowedComment = !this.settingsPerProfile.notifyOnNonFollowedComment
    this.updateGeneral(this.settingsPerProfile)
  }

  toggleRatingGeneral() {
    this.settingsPerProfile.notifyOnNonFollowedRating = !this.settingsPerProfile.notifyOnNonFollowedRating
    this.updateGeneral(this.settingsPerProfile)
  }

  update(settings : SettingsPerFollow) {
    this.notificationService.updatePerFollow(settings).subscribe(
      _ => this.notificationService.getForFollow().subscribe(
        data => {this.settingsPerFollow = data; this.order() }
      )
    )
  }

  updateGeneral(settings : SettingsPerProfile) {
    this.notificationService.updatePerProfile(settings).subscribe(
      _ => this.notificationService.getForProfile().subscribe(
        data => this.settingsPerProfile = data
      )
    )
  }

  order() {
    this.settingsPerFollow.sort((s1, s2) => s1.profile.localeCompare(s2.profile))
  }

}
