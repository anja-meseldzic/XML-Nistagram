import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';
import { Story } from '../../model/story';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  stories : Object[] = [];
  posts : Post[] = [];

  constructor(private mediaService : MediaService, private router : Router, private snackBar : MatSnackBar) {}

  ngOnInit(): void {
     this.mediaService.getStoriesForFeed().subscribe(
      data => { const stories : Story[] = data; this.constructSliderObjectsForStories(stories) },
      error => this.openSnackBar(error.error.message)
    );
    this.mediaService.getPostsForFeed().subscribe(
      data => { this.posts = data; this.constructSliderObjectsForPosts(); },
      error => this.openSnackBar(error.error.message)
    );
  }

  constructSliderObjectsForStories(stories : Story[]) {
    this.stories = [];
    const usernames : String[] = Array.from(new Set(stories.map(story => story.username)));
    for(const username of usernames) {
      const userStories = stories.filter(story => story.username === username)
      const storyObject = new Array<Object>();
      for(const userStory of userStories) {
        const link = userStory.link != null && userStory.link.length > 0 ? userStory.link : ''
        if(userStory.url.endsWith('.jpg') || userStory.url.endsWith('.png')) {
          storyObject.push( {username: userStory.username, link: userStory.link, id: userStory.id, image: environment.mediaBaseUrl + userStory.url, thumbImage: environment.mediaBaseUrl + userStory.url, title: userStory.username + ' ' + link});
        } else {
          storyObject.push({username: userStory.username, link: userStory.link, id: userStory.id, video: environment.mediaBaseUrl + userStory.url, alt: 'video unavailable', title: userStory.username + ' ' + link});
        }
      }
      this.stories.push(storyObject);
    }
    console.log(this.stories);
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

  public seeDetails(id) {
    this.router.navigate(['./post/' + id]);
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }

  public storyClick(event, storyList : Object[]) {
    var s = storyList[event]
    this.mediaService.sendStoryLinkClick(s['id']).subscribe()
    if(s['link'] != null && s['link'].length > 0) {
      document.location.href = s['link']
    }
  }
}
