import { Component, OnInit } from '@angular/core';
import { InappropriateListDTO } from '../../DTOs/inappropriate-list-dto';
import { MediaService } from '../../media-service/media.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inappropriate-content',
  templateUrl: './inappropriate-content.component.html',
  styleUrls: ['./inappropriate-content.component.css']
})
export class InappropriateContentComponent implements OnInit {

  displayedColumns: string[] = ['reason', 'username', 'post', 'shutProfile','deletePost','ignoreComplaint'];
  dataSource : InappropriateListDTO[] = [];

  constructor(private mediaService: MediaService, private router : Router, private snackBar : MatSnackBar) { }

  ngOnInit(): void {
    this.getInappropriateContent();
  }

  getInappropriateContent(){
    this.mediaService.getInappropriateContent().subscribe(
      data => { this.dataSource = data; },
      error => this.openSnackBar(error.error.message)
    );
  }

  viewPost(element : InappropriateListDTO){
    this.router.navigate(['./post/' + element.postId]);
  }

  shutProfile(element : InappropriateListDTO){
    this.mediaService.shutDownProfile(element).subscribe(
      (data) => {      
        
       this.getInappropriateContent();
   },
   error => {
    this.openSnackBar(error.error.message)
   } );
  }

  deletePost(element : InappropriateListDTO){
    this.mediaService.deleteInappropriateContent(element).subscribe(
      (data) => {      
        
       this.getInappropriateContent();
   },
   error => {
    this.openSnackBar(error.error.message)
   } );
  }

  ignoreComplaint(element : InappropriateListDTO){
    this.mediaService.approveInappropriateContent(element).subscribe(
      (data) => {      
        
       this.getInappropriateContent();
   },
   error => {
    this.openSnackBar(error.error.message)
   } );
  }
  
  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }


}
