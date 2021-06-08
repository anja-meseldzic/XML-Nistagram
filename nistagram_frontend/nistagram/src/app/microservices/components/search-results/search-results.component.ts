import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
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
      const value = this.value.replace('#', '%23');
      this.mediaService.getPostsBySearchCriteria(new SearchResult(value, this.type)).subscribe(
        data => { this.posts = data; this.constructSliderObjectsForPosts(); },
        error => console.log(error.error.message)
      );
    });
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

  public seeDetails(id : String) {
    this.router.navigate(['../post/' + id]);
  }
}
