import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { NgImageSliderComponent } from 'ng-image-slider';
import { environment } from 'src/environments/environment';
import { AuthService } from '../../auth-service/auth.service';
import { CollectionInfoDto } from '../../DTOs/collection-info-dto';
import { MediaService } from '../../media-service/media.service';
import { InfluencerCampaign } from '../../model/influencer-campaign';
import { Post } from '../../model/post';
import { ProfileInfo } from '../../model/profile-info';
import { Story } from '../../model/story';
import { ProfileService } from '../../profile-service/profile.service';
import { CloseFriendsComponent } from '../close-friends/close-friends.component';
import { CollectionDialogComponent } from '../collection-dialog/collection-dialog.component';
import { FollowerRequestDialogComponent } from '../follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from '../followers-dialog/followers-dialog.component';
import { InfluencerCampaignDialogComponent } from '../influencer-campaign-dialog/influencer-campaign-dialog.component';
import { RatingsDialogComponent } from '../ratings-dialog/ratings-dialog.component';
import {ShareDialogComponent} from '../share-dialog/share-dialog.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService, private matDialog : MatDialog, private snackBar : MatSnackBar, public authService : AuthService) { }

  profile : ProfileInfo = new ProfileInfo(0, '', '', '', new Date(1998, 11, 29), '', '', '', 0, 0, false, false, false,false);
  posts : Post[] = [];
  favourites : Post[] = [];
  stories : Story[] = [];
  allStories : Story[] = [];
  storyHighlights : Story[] = [];
  collections : CollectionInfoDto[] = [];
  collections1 = [];
  

  @ViewChild('nav') slider: NgImageSliderComponent;


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
          this.mediaService.getCollections().subscribe(
            data => { this.collections = data; this.constructSliderObjectsForCollections(this.collections); },
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
          this.mediaService.getHighlights().subscribe(
            data => { this.storyHighlights = data; this.constructSliderObjectsForHighlights(); },
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
          storyObject.push( {image: environment.mediaBaseUrl + url, thumbImage: environment.mediaBaseUrl + url});
        } else {
          storyObject.push({video: environment.mediaBaseUrl + url, alt: 'video unavailable'});
        }
      }
      post['slider'] = storyObject;
    }
  }

  constructSliderObjectsForCollections(collections : CollectionInfoDto[]){
    this.collections1 = [];
    const names : String[] = Array.from(new Set(collections.map(c => c.name)));
    for(const name of names) {
      const nameCollections = collections.filter(c => c.name === name)
      const storyObject = new Array<Object>();
      for(const collection of nameCollections) {
        for(const url of collection.urls){
          if(url.endsWith('.jpg') || url.endsWith('.png')) {
            storyObject.push( {image: environment.mediaBaseUrl + url, thumbImage: environment.mediaBaseUrl + url, title: collection.name});
          } else {
            storyObject.push({video: environment.mediaBaseUrl + url, alt: 'video unavailable', title: collection.name});
          }
        }
      }
      this.collections1.push(storyObject);
    }
  }

  constructSliderObjectsForFav() {
    for(const fav of this.favourites) {
      const storyObject = new Array<Object>();
      for(const url of fav.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: environment.mediaBaseUrl + url, thumbImage: environment.mediaBaseUrl + url});
        } else {
          storyObject.push({video: environment.mediaBaseUrl + url, alt: 'video unavailable'});
        }
      }
      fav['slider'] = storyObject;
    }
  }

  constructSliderObjectsForStories() {
    const storyObject = new Array<Object>();
    for(const story of this.stories) {
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: environment.mediaBaseUrl + story.url, thumbImage: environment.mediaBaseUrl + story.url, title: story.link});
      } else {
        storyObject.push({video: environment.mediaBaseUrl + story.url, alt: 'video unavailable', title: story.link});
      }
    }
    this.stories['slider'] = storyObject;
  }

  constructSliderObjectsForHighlights() {
    const storyObject = new Array<Object>();
    for(const story of this.storyHighlights) {
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: environment.mediaBaseUrl + story.url, thumbImage: environment.mediaBaseUrl + story.url});
      } else {
        storyObject.push({video: environment.mediaBaseUrl + story.url, alt: 'video unavailable'});
      }
    }
    this.storyHighlights['slider'] = storyObject;
  }

  constructSliderObjectsForAllStories() {
    for(const story of this.allStories) {
      const storyObject = new Array<Object>();
      if(story.url.endsWith('.jpg') || story.url.endsWith('.png')) {
        storyObject.push( {image: environment.mediaBaseUrl + story.url, thumbImage: environment.mediaBaseUrl + story.url});
      } else {
        storyObject.push({video: environment.mediaBaseUrl + story.url, alt: 'video unavailable'});
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

  block(username: string): void {
    const myUsername = this.authService.getUsername();
    this.profileService.changeBlocked(username, myUsername)
      .subscribe(data => this.router.navigate(['profile/' + myUsername]));
  }

  public seeDetails(id : String) {
    this.router.navigate(['../post/' + id]);
  }

  editProfile(): void {
    this.router.navigate(['/personal-edit']);
  }

  settings(): void {
    this.router.navigate(['/profile-config']);
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

  ratings(){
    this.matDialog.open(RatingsDialogComponent, { width: '30vw',
      maxWidth: '30vw'});
  }

  addToHighlights(story : Story){
    this.mediaService.saveToHighlights(story).subscribe(data => {this.snackBar.open(data, "Okay");
    this.mediaService.getHighlights().subscribe(
      data => { this.storyHighlights = data; this.constructSliderObjectsForHighlights(); },
      error => {this.openSnackBar(error.error.message); this.router.navigate(['./feed']);}
    );
  });

  }

  addToCollection(post : Post){
    this.matDialog.open(CollectionDialogComponent, {data : post.id,  width: '30vw',
      maxWidth: '30vw'}).afterClosed().subscribe(
        _ => this.mediaService.getFavouritesForUser().subscribe(
          data => { this.favourites = data; this.constructSliderObjectsForFav();
          this.mediaService.getCollections().subscribe(_data => {
            this.collections = _data;
            this.constructSliderObjectsForCollections(_data);
          }) },
          error => console.log(error.error.message)
        )
      );
  }

  public includes(id : Number) : boolean {
    return this.storyHighlights.filter(h => h.id === id).length > 0;
  }

  shareStories = () => {
    this.matDialog.open(ShareDialogComponent, { width: '30vw', maxWidth: '30vw'});
  }

  chooseCampaigns(){
    this.profileService.getCampaignsForInfluencers().subscribe(data => {
      this.matDialog.open(InfluencerCampaignDialogComponent, {data : data,  width: '70vw',
      maxWidth: '70vw', height: '800px'})
    });
  }
  public storyClick(event) {
    const s = this.stories[event]
    this.mediaService.sendStoryLinkClick(s['id']).subscribe()
    if(s.link != null && s.link.length > 0) {
      document.location.href = s.link.toString()
    }
  }
}
