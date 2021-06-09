import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import axios from 'axios';
import {Router} from '@angular/router';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  name = '';
  lastName = '';
  email = '';
  phoneNumber = '';
  website = '';
  biography = '';
  birthDate = '';
  gender = 'Select gender';
  username = '';
  password = '';

  // tslint:disable-next-line:variable-name
  constructor(private _snackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  register(): void {
    if (!this.validateRequiredFields()) {
      this.openSnackBar('First name, Last name, Birth date, Gender, Username and Password are required', 'Okay');
      return;
    }
    if (!this.validateRegExp('^[0-9]*$', this.phoneNumber)) {
      this.openSnackBar('Phone number can take only digits', 'Okay');
      return;
    }
    if (!this.validateRegExp('^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})?$', this.email)) {
      this.openSnackBar('Email is not valid', 'Okay');
      return;
    }
    if (!this.validateRegExp('^(((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*)?$', this.website)) {
      this.openSnackBar('Website url is not valid', 'Okay');
      return;
    }

    axios
      // .post('http://localhost:8081/regulars', {
      .post(environment.authBaseUrl + 'regulars', {
        name : this.name,
        surname : this.lastName,
        email : this.email,
        phoneNumber : this.phoneNumber,
        birthDate : this.birthDate + 'T00:00:00.123Z',
        website : this.website,
        biography : this.biography,
        gender : this.gender,
        user : {
          username : this.username,
          password : this.password,
          role : 'USER'
        }
      })
      .then(res => this.openSnackBar('Success', 'Okay'))
      .catch(e => this.openSnackBar('Username is already taken', 'Okay'));
  }

  cancel(): void {
    this.router.navigate(['/']);
  }

  validateRequiredFields(): boolean {
    return this.name.trim() !== '' && this.lastName !== '' && this.birthDate.trim() !== '' && this.gender.trim() !== 'Select gender'
      && this.username.trim() !== '' && this.password.trim() !== '';
  }

  validateRegExp(reg: string, toTest: string): boolean {
    const regExp = new RegExp(reg);
    return regExp.test(toTest);
  }

  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }

}
