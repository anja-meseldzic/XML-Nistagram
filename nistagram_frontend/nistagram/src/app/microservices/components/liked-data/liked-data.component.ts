import { Component, OnInit } from '@angular/core';
import { Post } from '../../model/post';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { MediaService } from '../../media-service/media.service';

@Component({
  selector: 'app-liked-data',
  templateUrl: './liked-data.component.html',
  styleUrls: ['./liked-data.component.css']
})
export class LikedDataComponent implements OnInit {

  posts : Post[] = [];
  constructor(private mediaService : MediaService, private router : Router, private snackBar : MatSnackBar) { }

  ngOnInit(): void {
    this.mediaService.getLikedContent().subscribe(
      data => { this.posts = data; this.constructSliderObjectsForPosts(); },
      error => this.openSnackBar(error.error.message)
    );
  }

  constructSliderObjectsForPosts() {
    for(const post of this.posts) {
      const storyObject = new Array<Object>();
      // post.urls.forEach(url => {
      //   url = environment.mediaBaseUrl + url;
      //   console.log(url)
      // });
      // for(let url of post.urls) {
      //   url = environment.mediaBaseUrl + url;
      // }
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

}
