import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { MediaService } from '../media-service/media.service';

@Component({
  selector: 'app-create-story',
  templateUrl: './create-story.component.html',
  styleUrls: ['./create-story.component.css']
})
export class CreateStoryComponent implements OnInit {

  public closeFriends;
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";
  public close : string;

  constructor(private mediaService : MediaService, private _snackBar: MatSnackBar, private router : Router) { 
    this.closeFriends = 0;
  }

  ngOnInit(): void {
  }
  submit(){
    const fd = new FormData();
    if(this.selectedFile != null){
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      console.log(this.selectedFile.name);

      this.close = this.closeFriends == 0 ? "true" : "false";
      fd.append('story', this.close);

      this.mediaService.postStory(fd).subscribe(
         (data) => {
          let message = "Story is posted. ";
          this.openSnackBar(message, "Okay");
      },
      error => {
        this.openSnackBar(error.error, "Okay");
      }
      
      );
    }
  }
  
  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.selectedFileHide = false;
    this.fileName = "Data selected";
    this.fileExtension = this.selectedFile.name.split('?')[0].split('.').pop();
    console.log(this.selectedFile.name);
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
