import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';
import { ProfileInfo } from '../../model/profile-info';
import { Story } from '../../model/story';
import { ProfileService } from '../../profile-service/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService) { }

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
    this.profile = this.profileService.getProfileInfo(username);
    if(this.profile == null) {
      this.router.navigate(['../feed']);
    }
    if(this.profile.owned || this.profile.following || !this.profile.privateProfile) {
      this.posts = this.mediaService.getPostsByUser(this.profile.username);
      this.stories = this.mediaService.getStoriesByUser(this.profile.username);
      this.constructSliderObjectsForPosts();
      this.constructSliderObjectsForStories();
    }
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
      console.log("Follow request have been sent.")
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
}
