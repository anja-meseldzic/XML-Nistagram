import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from './microservices/auth-service/auth.service';
import { MediaService } from './microservices/media-service/media.service';
import { SearchResult } from './microservices/model/search-result';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'nistagram';

  
  
  constructor(private mediaService : MediaService, private router : Router, public authService : AuthService) {
  }

  options: SearchResult[] = [];
  data : String = '';

  public updated() {
    this.options = [];
    if (this.data.length > 0) {
      this.mediaService.getSearchResults(this.data).subscribe(
        data => { this.options = data; this.assignIds(); }
      );
    }
  }

  assignIds() {
    let i = 0;
    for(let option of this.options) {
      option['id'] = i++;
    }
  }

  public seeDetails(id) {
    const res : SearchResult = this.options.filter(o => o['id'] === id)[0];
    if(res.type == 'profile') {
      this.router.navigate(['../profile/' + res.name]);
    } else if(res.type == 'location' || res.type == 'hashtag') {
      this.router.navigate(['../post'], { queryParams: { type: res.type, value: res.name }});
    }
  }

  public goToProfile() {
    this.router.navigate(['../profile/' + this.authService.getUsername()])
  }
}
