import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionDTO } from '../../DTOs/collection-dto';
import { MediaService } from '../../media-service/media.service';
import { ProfileService } from '../../profile-service/profile.service';

@Component({
  selector: 'app-collection-dialog',
  templateUrl: './collection-dialog.component.html',
  styleUrls: ['./collection-dialog.component.css']
})
export class CollectionDialogComponent implements OnInit {

  public name : String;
  constructor(@Inject(MAT_DIALOG_DATA) public data : Number, private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService, private matDialog : MatDialog, private _snackBar: MatSnackBar ) { }

  ngOnInit(): void {
  }

  submit(){
    this.mediaService.addToCollection(new CollectionDTO(this.data, this.name)).subscribe(data => console.log(data));
  }
}
