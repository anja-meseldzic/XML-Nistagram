import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { FollowRequestDto } from '../../DTOs/follow-request-dto';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';
import { ProfileInfo } from '../../model/profile-info';
import { Story } from '../../model/story';
import { ProfileService } from '../../profile-service/profile.service';
import { CloseFriendsComponent } from '../close-friends/close-friends.component';
import { FollowerRequestDialogComponent } from '../follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from '../followers-dialog/followers-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private _snackBar : MatSnackBar,private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService, private matDialog : MatDialog) { }

  profile : ProfileInfo = new ProfileInfo(0, '', '', '', new Date(1998, 11, 29), '', '', '', 0, 0, false, false, false);
  posts : Post[] = [];
  stories : Story[] = [];

  ngOnInit(): void {
    this.route.paramMap.subscribe(
      params => {const username = params.get('username');
                 this.getProfileInfo(username);
      }
    )
  }

  getProfileInfo(username : String) {
    this.profileService.getProfileInfo(username).subscribe(
      data => {
        this.profile = data;
        if(this.profile == null) {
          this.router.navigate(['../feed']);
        }
        if(this.profile.owned || this.profile.following || !this.profile.privateProfile) {
          this.mediaService.getPostsByUser(this.profile.username).subscribe(
            data => { this.posts = data; this.constructSliderObjectsForPosts(); },
            error => console.log(error.error.message)
          )
          this.mediaService.getStoriesByUser(this.profile.username).subscribe(
            data => { this.stories = data; this.constructSliderObjectsForStories(); },
            error => console.log(error.error.message)
          );
        }
      }
    );
  }

  constructSliderObjectsForPosts() {
    for(const post of this.posts) {
      const storyObject = new Array<Object>();
      for(const url of post.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: url, thumbImage: url});
        } else {
          storyObject.push({video: url, alt: 'video unavailable'});
        }
      }
      post['slider'] = storyObject;
    }
  }

  constructSliderObjectsForStories() {
    const storyObject = new Array<Object>();
    for(const story of this.stories) {
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: story.url, thumbImage: story.url});
      } else {
        storyObject.push({video: story.url, alt: 'video unavailable'});
      }
    }
    this.stories['slider'] = storyObject;
  }

  follow(privateProfile : boolean, profileUsername : string){
    if(!privateProfile){
      this.profile.following = true;
      this.profileService.followProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
    }else{
      this.openSnackBar("You have successfully sent follow request.", "Okay")
      this.profileService.followProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
    }
  }
  unfollow(profileUsername : string){
    this.profile.following = false;
    this.profileService.unfollowProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
  }

  public seeDetails(id : String) {
    this.router.navigate(['../post/' + id]);
  }

  editProfile(): void {
    this.router.navigate(['/personal-edit']);
  }

  followRequests(){
    this.profileService.getFollowRequests(this.profile.username).subscribe(data =>{
      this.matDialog.open(FollowerRequestDialogComponent, {data : data});
    });
  }

  getFollowers(){
    this.profileService.getFollowers(this.profile.username).subscribe(data =>{
      console.log(data);
      this.matDialog.open(FollowersDialogComponent, {data : data});
    });
  }
  getFollowing(){
    this.profileService.getFollowing(this.profile.username).subscribe(data =>{
      this.matDialog.open(FollowersDialogComponent, {data : data});
    });
  }
  closeFriends(){
    this.profileService.getFollowers(this.profile.username).subscribe(data =>{
      console.log(data);
      this.matDialog.open(CloseFriendsComponent, {data : data,  width: '70vw',
      maxWidth: '70vw'});
    }); 
  }
    
  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
