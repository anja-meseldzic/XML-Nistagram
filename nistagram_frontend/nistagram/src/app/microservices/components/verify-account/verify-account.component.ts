import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ProfileVerificationRequest } from '../../DTOs/profile-verification-request';
import { MediaService } from '../../media-service/media.service';
import { ProfileService } from '../../profile-service/profile.service';

interface Category{
  value : String;
}

@Component({
  selector: 'app-verify-account',
  templateUrl: './verify-account.component.html',
  styleUrls: ['./verify-account.component.css']
})

export class VerifyAccountComponent implements OnInit {

  constructor(private profileService : ProfileService, private snackBar : MatSnackBar) { }

  name: String;
  lastname : String;
  selectedFileHide = true;
  selectedFile: File = null;
  fileName = "";
  fileExtension = "";
  selectedValue  = "";

  categories: Category[] = [
    {value: 'Influencer'},
    {value: 'Sports'},
    {value: 'News/Media'},
    {value: 'Business'},
    {value: 'Brand'},
    {value: 'Organisation'}
  ];
  ngOnInit(): void {
  }

  submit(){
    const fd = new FormData();
    if(this.selectedFile != null){
      fd.append('imageFile', this.selectedFile,  this.selectedFile.name);
      console.log(this.selectedFile.name);
      var request =  new ProfileVerificationRequest(0,this.name, this.lastname, this.selectedValue, "");
      fd.append('request', JSON.stringify(request));

      this.profileService.sendProfileVerifactionRequest(fd).subscribe(
         data => {
          let message = data;
          this.openSnackBar(message, "Okay");
      },
      error => {
        this.openSnackBar(error.error, "Okay");
      }
      
      );
    }else{
      this.openSnackBar("You have to select a file", "Okay");
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
    this.snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
