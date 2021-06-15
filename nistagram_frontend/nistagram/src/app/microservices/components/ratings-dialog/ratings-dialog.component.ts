import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ratings-dialog',
  templateUrl: './ratings-dialog.component.html',
  styleUrls: ['./ratings-dialog.component.css']
})
export class RatingsDialogComponent implements OnInit {

  constructor(private router : Router) { }

  ngOnInit(): void {
  }

  getLikedData(){
    this.router.navigate(['./likedData']);

  }
  getDislikedData(){
    this.router.navigate(['./dislikedData']);
  }
}
