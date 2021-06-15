import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from '../../media-service/media.service';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { InappropriateDTO } from '../../DTOs/inappropriate-dto';

@Component({
  selector: 'app-report-dialog',
  templateUrl: './report-dialog.component.html',
  styleUrls: ['./report-dialog.component.css']
})
export class ReportDialogComponent implements OnInit {

  public content : string;
  constructor(@Inject(MAT_DIALOG_DATA) public data : Number,private _snackBar: MatSnackBar, private mediaService : MediaService) { }
  
  ngOnInit(): void {
  }

  submit(){
    this.mediaService.reportContent(new InappropriateDTO(this.data,this.content)).subscribe(
      (data) => {      
        let message = data;
       this.openSnackBar(message, "Okay");
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   } );

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
