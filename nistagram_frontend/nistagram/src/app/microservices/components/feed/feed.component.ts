import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  
  constructor(private mediaService : MediaService, private router : Router) {}

  ngOnInit(): void {
     this.mediaService.getStoriesForFeed().subscribe(
      data => { const stories : Story[] = data; this.constructSliderObjectsForStories(stories) },
      error => console.log(error.error.message)
    );
    this.mediaService.getPostsForFeed().subscribe(
      data => { this.posts = data; this.constructSliderObjectsForPosts(); }
    );
  }

  constructSliderObjectsForStories(stories : Story[]) {
    this.stories = [];
    const usernames : String[] = Array.from(new Set(stories.map(story => story.username)));
    for(const username of usernames) {
      const userStories = stories.filter(story => story.username === username)
      const storyObject = new Array<Object>();
      for(const userStory of userStories) {
        if(userStory.url.endsWith('.jpg') || userStory.url.endsWith('.png')) {
          storyObject.push( {image: userStory.url, thumbImage: userStory.url, title: username});
        } else {
          storyObject.push({video: userStory.url, alt: 'video unavailable', title: username});
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
          storyObject.push( {image: url, thumbImage: url});
        } else {
          storyObject.push({video: url, alt: 'video unavailable'});
        }
      }
      post['slider'] = storyObject;
    }
  }

  public seeDetails(id) {
    this.router.navigate(['./post/' + id]);
  }
}
