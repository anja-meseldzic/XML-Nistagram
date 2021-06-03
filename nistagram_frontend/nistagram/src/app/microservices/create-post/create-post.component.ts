import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PostDTO } from '../DTOs/post-dto';
import { MediaService } from '../media-service/media.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  public description : string;
  public location : string;
  public tag : string;
  public dataSource: string[] = [];
  displayedColumns: string[] = ['tag'];
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";

  constructor(private mediaService : MediaService, private _snackBar: MatSnackBar, private router : Router) { }

  ngOnInit(): void {
  }

  submit(){
    const fd = new FormData();
    if(this.selectedFile != null){
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      console.log(this.selectedFile.name);
      var post =  new PostDTO(this.dataSource,this.location, this.description);
      fd.append('post', JSON.stringify(post));

      this.mediaService.postData(fd).subscribe(
         (data) => {
          let message = "Post is created. ";
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
  OnClick(){
    if(this.tag == ""){
      this.openSnackBar("Please fill out the tag field.", "Okay");
    }
    else {
      this.dataSource.push(this.tag);
    
      var dataSource2 = this.dataSource;
      this.dataSource= [];
      for (let i=0; i<dataSource2.length; i++){
        this.dataSource.push(dataSource2[i]);
      }
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
