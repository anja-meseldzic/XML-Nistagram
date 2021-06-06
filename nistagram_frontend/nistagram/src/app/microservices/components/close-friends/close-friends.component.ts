import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { FollowerDto } from '../../DTOs/follower-dto';
import { MediaService } from '../../media-service/media.service';
import { ProfileInfo } from '../../model/profile-info';
import { ProfileService } from '../../profile-service/profile.service';

@Component({
  selector: 'app-close-friends',
  templateUrl: './close-friends.component.html',
  styleUrls: ['./close-friends.component.css']
})
export class CloseFriendsComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data : FollowerDto[], private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService, private matDialog : MatDialog, private _snackBar: MatSnackBar ) { }

  profile : ProfileInfo = new ProfileInfo(0, '', '', '', new Date(1998, 11, 29), '', '', '', 0, 0, false, false, false);
  displayedColumns: string[] = ['username', 'addClose'];
  displayedColumns2: string[] = ['username2', 'removeClose'];
  dataSource = this.data;
  public closeFriends : String[] = [];

  ngOnInit(): void {
    this.getCloseFriends();
  }

  Remove(usernameOfFriend : string){
    this.profileService.removeCloseFriend(usernameOfFriend).subscribe(
      (data) => {      
        let message = data;
       this.openSnackBar(message, "Okay");
       this.getCloseFriends();
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   } );
  }

  getCloseFriends(){
    this.profileService.getCloseFriends().subscribe(
      (data) => {      
        this.closeFriends = data;
   },
   error => {
     this.openSnackBar(error.error, "Okay");
   } );
  }

  OnClick(usernameOfFriend : string){
   
    this.profileService.addCloseFriend(usernameOfFriend).subscribe(
      (data) => {      
        let message = data;
       this.openSnackBar(message, "Okay");
       this.getCloseFriends();
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
