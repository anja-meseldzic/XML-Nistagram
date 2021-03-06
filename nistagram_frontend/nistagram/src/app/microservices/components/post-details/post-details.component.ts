import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AllCommentDTO } from '../../DTOs/all-comment-dto';
import { CommentDTO } from '../../DTOs/comment-dto';
import { RatingDTO } from '../../DTOs/rating-dto';
import { MediaService } from '../../media-service/media.service';
import { Post } from '../../model/post';
import { ReportDialogComponent } from '../report-dialog/report-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import axios from 'axios';
import {AuthService} from '../../auth-service/auth.service';
import {ShareDialogComponent} from '../share-dialog/share-dialog.component';


@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {

  post : Post;
  peer: '';
  public comments : AllCommentDTO[] = [];
  public likes : number = 0;
  public dislikes : number = 0;
  public comment : string;
  public hidden : boolean = false;

  public id = 0;

  constructor(private mediaService : MediaService, private route : ActivatedRoute, private router : Router, private _snackBar: MatSnackBar, private matDialog : MatDialog) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.id = Number(id)
    this.getPost(id);
    this.getComments(Number(id));
    this.getRatingsNumber(Number(id));
  }

  reactions(){
    this.router.navigate(['./reactions/' + this.route.snapshot.paramMap.get('id') ]);
  }

  getRatingsNumber(id: number) {
    this.mediaService.getReactionsNumber(id).subscribe(
      (data) => {
        this.likes = data.likes;
        this.dislikes = data.dislikes;
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   } );
  }

  like(){
    this.mediaService.reactOnPost(new RatingDTO(Number(this.route.snapshot.paramMap.get('id')), true)).subscribe(
      (data) => {
        this.getRatingsNumber(Number(this.route.snapshot.paramMap.get('id')));
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   } );
  }

  dislike(){
    this.mediaService.reactOnPost(new RatingDTO(Number(this.route.snapshot.paramMap.get('id')), false)).subscribe(
      (data) => {
        this.getRatingsNumber(Number(this.route.snapshot.paramMap.get('id')));
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   } );
  }

  getComments(id : number){
    this.mediaService.getComments(id).subscribe(
      (data) => this.comments = data
    );
  }

  postComment(){
    this.mediaService.postComment(new CommentDTO(Number(this.route.snapshot.paramMap.get('id')),this.comment)).subscribe(
      (data) => {
       let message = "Comment is uploaded. ";
       this.openSnackBar(message, "Okay");
       this.getComments(Number(this.route.snapshot.paramMap.get('id')));
       this.comment="";
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
    this.mediaService.getPost(id).subscribe(
      data => {
        this.post = data;
        if(this.post == null) {
          this.router.navigate(['../feed']);
        } else {
          this.post.urls.forEach(url => url = environment.mediaBaseUrl + url);
          this.constructSliderObjectsForPost();
        }
      },
      error => { this.openSnackBar(error.error.message, "Okay"); this.router.navigate(['./feed']) }
    )
  }

  constructSliderObjectsForPost() {
    const storyObject = new Array<Object>();
      for(const url of this.post.urls) {
        if(url.endsWith('.jpg') || url.endsWith('.png')) {
          storyObject.push( {image: environment.mediaBaseUrl + url, thumbImage: environment.mediaBaseUrl + url});
        } else {
          storyObject.push({video: environment.mediaBaseUrl + url, alt: 'video unavailable'});
        }
      }
      this.post['slider'] = storyObject;
  }

  public goToProfile(username : String) {
    this.router.navigate(['../profile/' + username])
  }

  public favourites(id :Number){
    this.mediaService.saveToFavourites(id).subscribe(data => console.log(data));
    this._snackBar.open("You have successfully saved post to favourites.", "Okay");
    this.hidden = true;
  }

  reportPost(){
    this.matDialog.open(ReportDialogComponent, {data : Number(this.route.snapshot.paramMap.get('id')),  width: '30vw',
      maxWidth: '30vw'})
  }

  share = () => {
    this.matDialog.open(ShareDialogComponent, { width: '30vw', maxWidth: '30vw'});
  }

  sendClick() {
    this.mediaService.sendPostLinkClick(this.id).subscribe()
  }
}
