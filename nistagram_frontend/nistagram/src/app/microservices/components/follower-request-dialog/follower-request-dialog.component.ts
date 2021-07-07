import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FollowRequestDto } from '../../DTOs/follow-request-dto';
import { ProfileService } from '../../profile-service/profile.service';


@Component({
  selector: 'app-follower-request-dialog',
  templateUrl: './follower-request-dialog.component.html',
  styleUrls: ['./follower-request-dialog.component.css']
})
export class FollowerRequestDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data : FollowRequestDto[], private profileService : ProfileService) { }

  ngOnInit(): void {
  }


  displayedColumns: string[] = ['username', 'action1', 'action2'];
  dataSource = this.data;

  public follow(username : string){
    this.profileService.acceptRequest(username).subscribe(data => this.dataSource = data);
  }

  public delete(username : string){
    this.profileService.deleteRequest(username).subscribe(data => this.dataSource = data);
  }
}
