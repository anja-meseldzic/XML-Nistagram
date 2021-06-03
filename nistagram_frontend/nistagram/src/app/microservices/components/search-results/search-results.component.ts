import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';
import { SearchResult } from '../../model/search-result';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  constructor(private route : ActivatedRoute, private mediaService : MediaService, private router : Router) { }

  posts : Post[] = [];
  type : String = '';
  value : String = '';

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.type = params['type'];
      this.value = params['value'];
      this.posts = this.mediaService.getPostsBySearchCriteria(new SearchResult(this.value, this.type));
      this.constructSliderObjectsForPosts();
    });
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

  public seeDetails(id : String) {
    this.router.navigate(['../post/' + id]);
  }
}
