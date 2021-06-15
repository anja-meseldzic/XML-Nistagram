import { Component, OnInit } from '@angular/core';
import {ProfileService} from '../profile-service/profile.service';
import jwtDecode from 'jwt-decode';
import {Router} from '@angular/router';

@Component({
  selector: 'app-profile-config',
  templateUrl: './profile-config.component.html',
  styleUrls: ['./profile-config.component.css']
})
export class ProfileConfigComponent implements OnInit {
  privacy = 'public';
  msgPermission = 'allow';
  tagPermission = 'allow';
  following = [];
  muted = ['pera'];
  username: string;

  constructor(private profileService: ProfileService, private router: Router) { }

  ngOnInit(): void {
    const jwt = localStorage.getItem('jwt');
    const jwtDec = jwtDecode(jwt);

    // @ts-ignore
    this.profileService.getFollowing(jwtDec.username).subscribe(data => this.following = data.map(d => d.username));
  }

  edit(): void {
    console.log('edit');
  }
}
