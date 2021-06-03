import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import axios from 'axios';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  name: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  website: string;
  biography: string;
  birthDate: string;
  gender = 'Select gender';
  username: string;
  password: string;

  // tslint:disable-next-line:variable-name
  constructor(private _snackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  register(): void {
    axios
      .post('http://localhost:8081/regulars', {
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
      .then(res => this.openSnackBar('Success', 'Okay'));
  }

  cancel(): void {
    this.router.navigate(['/']);
  }

  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }

}
