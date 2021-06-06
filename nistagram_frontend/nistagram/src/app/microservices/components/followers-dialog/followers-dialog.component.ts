import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { FollowerDto } from '../../DTOs/follower-dto';
import { MediaService } from '../../media-service/media.service';
import { ProfileInfo } from '../../model/profile-info';
import { ProfileService } from '../../profile-service/profile.service';

@Component({
  selector: 'app-followers-dialog',
  templateUrl: './followers-dialog.component.html',
  styleUrls: ['./followers-dialog.component.css']
})
export class FollowersDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data : FollowerDto[], private route : ActivatedRoute, private router : Router, private profileService : ProfileService, private mediaService : MediaService, private matDialog : MatDialog) { }

  displayedColumns: string[] = ['username'];
  dataSource = this.data;
  
  ngOnInit(): void {
   
  }
  
}
