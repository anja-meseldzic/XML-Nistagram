import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import { MatSnackBar } from '@angular/material/snack-bar';
import {UserServiceService} from "../user-service/user-service.service";
import {User} from "../models/user";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  firstName = '';
  lastName = '';
  username = '';
  password = '';

  constructor(private router: Router, private userService: UserServiceService) { }

  ngOnInit(): void {
  }

  register(): void {
    if (!this.validateRequiredFields()) {
      alert('First name, Last name, Username and Password are required');
      return;
    }

    this.userService.register(new User(this.firstName, this.lastName, this.username, this.password))
      .subscribe(_ => alert('Success'), _ => alert('This username is already taken'))
  }

  cancel(): void {
    this.router.navigate(['/']);
  }

  validateRequiredFields(): boolean {
    return this.firstName.trim() !== '' && this.lastName.trim() !== ''
      && this.username.trim() !== '' && this.password.trim() !== '';
  }
}
