import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FollowerDto } from '../../DTOs/follower-dto';

@Component({
  selector: 'app-followers-dialog',
  templateUrl: './followers-dialog.component.html',
  styleUrls: ['./followers-dialog.component.css']
})
export class FollowersDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data : FollowerDto[]) { }

  displayedColumns: string[] = ['username'];
  dataSource = this.data;
  
  ngOnInit(): void {
  }
  
}
