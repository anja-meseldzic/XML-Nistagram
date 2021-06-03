import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {

  post : Post;
  
  constructor(private mediaService : MediaService, private route : ActivatedRoute, private router : Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.getPost(id);
  }

  getPost(id : String) {
    this.post = this.mediaService.getPost(id);
    if(this.post == null) {
      this.router.navigate(['../feed']);
    } else {
      this.constructSliderObjectsForPost();
    }
  }

  constructSliderObjectsForPost() {
    const storyObject = new Array<Object>();
      for(const url of this.post.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: url, thumbImage: url});
        } else {
          storyObject.push({video: url, alt: 'video unavailable'});
        }
      }
      this.post['slider'] = storyObject;
  }

  public goToProfile(username : String) {
    this.router.navigate(['../profile/' + username])
  }
}
