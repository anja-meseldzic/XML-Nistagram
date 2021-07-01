import { Component, OnInit } from '@angular/core';
import {UserServiceService} from "../user-service/user-service.service";
import {AuthServiceService} from "../auth-service/auth-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';

  constructor(private userService: UserServiceService, private router: Router) { }

  ngOnInit(): void {
  }

  login(): void {
    this.userService.login(this.username, this.password);
  }

  cancel(): void {
    this.router.navigate(['/']);
  }

}
