import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../auth-service/auth.service';
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

  constructor(private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService, private matDialog : MatDialog, private snackBar : MatSnackBar, public authService : AuthService) { }

  profile : ProfileInfo = new ProfileInfo(0, '', '', '', new Date(1998, 11, 29), '', '', '', 0, 0, false, false, false);
  posts : Post[] = [];
  favourites : Post[] = [];
  stories : Story[] = [];
  allStories : Story[] = [];

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
        if(this.profile.owned){
          this.mediaService.getFavouritesForUser().subscribe(
            data => { this.favourites = data; this.constructSliderObjectsForFav(); },
            error => console.log(error.error.message)
          );
          this.mediaService.getStoriesForUser().subscribe(
            data => { this.allStories = data; this.constructSliderObjectsForAllStories(); },
            error => console.log(error.error.message)
          );
        }
        if(this.profile.owned || this.profile.following || !this.profile.privateProfile) {
          this.mediaService.getPostsByUser(this.profile.username).subscribe(
            data => { this.posts = data; this.constructSliderObjectsForPosts(); },
            error => {this.openSnackBar(error.error.message); this.router.navigate(['./feed']);}
          )
          this.mediaService.getStoriesByUser(this.profile.username).subscribe(
            data => { this.stories = data; this.constructSliderObjectsForStories(); },
            error => {this.openSnackBar(error.error.message); this.router.navigate(['./feed']);}
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

  constructSliderObjectsForFav() {
    for(const fav of this.favourites) {
      const storyObject = new Array<Object>();
      for(const url of fav.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: url, thumbImage: url});
        } else {
          storyObject.push({video: url, alt: 'video unavailable'});
        }
      }
      fav['slider'] = storyObject;
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

  constructSliderObjectsForAllStories() {
   
    for(const story of this.allStories) {
      const storyObject = new Array<Object>();
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: story.url, thumbImage: story.url});
      } else {
        storyObject.push({video: story.url, alt: 'video unavailable'});
      }
      story['slider'] = storyObject;
    }
  }

  follow(privateProfile : boolean, profileUsername : string){
    if(!privateProfile){
      this.profile.following = true;
      this.profileService.followProfile(profileUsername).subscribe(data => this.profile.followerCount = Number(data));
    }else{
      this.openSnackBar("You have successfully sent follow request.")
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

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }

  closeFriends(){
    this.profileService.getFollowers(this.profile.username).subscribe(data =>{
      console.log(data);
      this.matDialog.open(CloseFriendsComponent, {data : data,  width: '70vw',
      maxWidth: '70vw'});
    }); 
  }
}
