import { Component, OnInit } from '@angular/core';
import {ProfileService} from '../profile-service/profile.service';
import jwtDecode from 'jwt-decode';
import {Router} from '@angular/router';
import {AuthService} from '../auth-service/auth.service';
import {Profile} from '../model/profile';

@Component({
  selector: 'app-profile-config',
  templateUrl: './profile-config.component.html',
  styleUrls: ['./profile-config.component.css']
})
export class ProfileConfigComponent implements OnInit {
  privacy = 'public';
  msgPermission = 'allow';
  tagPermission = 'allow';
  following = [];
  muted = ['pera'];
  profile: Profile;

  constructor(private authService: AuthService, private profileService: ProfileService, private router: Router) { }

  ngOnInit(): void {
    const username = this.authService.getUsername();

    // @ts-ignore
    this.profileService.getFollowing(username).subscribe(data => this.following = data.map(d => d.username));
    this.profileService.getProfile(username).subscribe(data => {
      this.profile = data;
      this.privacy = data.privateProfile ? 'private' : 'public';
      this.msgPermission = data.allowMessages ? 'allow' : 'deny';
      this.tagPermission = data.allowTagging ? 'allow' : 'deny';
    });
  }

  edit(): void {
    this.profile.privateProfile = this.privacy === 'private';
    this.profile.allowTagging = this.tagPermission === 'allow';
    this.profile.allowMessages = this.msgPermission === 'allow';
    this.profileService.editProfileSettings(this.profile).subscribe();
  }

  cancel(): void {
    this.router.navigate(['profile/' + this.authService.getUsername()]);
  }
}
