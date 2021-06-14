import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile-config',
  templateUrl: './profile-config.component.html',
  styleUrls: ['./profile-config.component.css']
})
export class ProfileConfigComponent implements OnInit {
  privacy = 'public';
  msgPermission = 'allow';
  tagPermission = 'allow';
  followers = ['ilija', 'pera', 'misko'];

  constructor() { }

  ngOnInit(): void {
  }

  edit(): void {
    console.log('edit');
  }
}
