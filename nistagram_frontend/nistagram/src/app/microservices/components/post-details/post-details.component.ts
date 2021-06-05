import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentDTO } from '../../DTOs/comment-dto';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {

  post : Post;
  public comments : string[] = ['very nice', 'cool', 'beautiful', 'lepo'];
  public likes : number = 50;
  public dislikes : number = 2 ;
  public comment : string;
  
  constructor(private mediaService : MediaService, private route : ActivatedRoute, private router : Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.getPost(id);
  }
  like(){

  }
  dislike(){

  }
  postComment(){
    this.mediaService.postComment(new CommentDTO(Number(this.route.snapshot.paramMap.get('id')),this.comment)).subscribe(
      (data) => {
       let message = "Comment is uploaded. ";
       this.openSnackBar(message, "Okay");
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   }
   
   );

  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
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