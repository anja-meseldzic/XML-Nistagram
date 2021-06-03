import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  login(): void {
    console.log('LOGIN');
  }

  cancel(): void {
    this.router.navigate(['/']);
  }
}
