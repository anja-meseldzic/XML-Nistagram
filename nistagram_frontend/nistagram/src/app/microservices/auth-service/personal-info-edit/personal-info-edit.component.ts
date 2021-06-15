import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { MatSnackBar } from '@angular/material/snack-bar';
import jwtDecode from 'jwt-decode';
import {Router} from '@angular/router';
import {environment} from '../../../../environments/environment';


@Component({
  selector: 'app-personal-info-edit',
  templateUrl: './personal-info-edit.component.html',
  styleUrls: ['./personal-info-edit.component.css']
})
export class PersonalInfoEditComponent implements OnInit {
  id: number;
  userId: number;
  name: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  website: string;
  biography: string;
  birthDate: string;
  gender = 'Select gender';
  username: string;
  originalUsername: string;

  // tslint:disable-next-line:variable-name
  constructor(private _snackBar: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
    const jwt = localStorage.getItem('jwt');
    if (jwt == null) {
      this.router.navigate(['/unauthorized']);
      return;
    }
    const jwtDec = jwtDecode(jwt);
    // @ts-ignore
    if (jwtDec.role !== 'USER') {
      this.router.navigate(['/unauthorized']);
      return;
    }


    axios
      // @ts-ignore
      .get(environment.authBaseUrl + 'regulars/' + jwtDec.id, {
        headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }
      })
      .then(res => {
        this.id = res.data.id;
        this.userId = res.data.user.id;
        this.name = res.data.name;
        this.lastName = res.data.surname;
        this.gender = res.data.gender;
        this.email = res.data.email;
        this.phoneNumber = res.data.phoneNumber;
        this.birthDate = this.buildDate(res.data.birthDate);
        this.website = res.data.website;
        this.biography = res.data.biography;
        this.username = res.data.user.username;
        this.originalUsername = res.data.user.username;
      });
  }

  edit(): void {
    if (!this.validateRequiredFields()) {
      this.openSnackBar('First name, Last name, Birth date, Gender and Username are required', 'Okay');
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
      .post(environment.authBaseUrl + 'regulars/update', {
        id : this.id,
        name : this.name,
        surname : this.lastName,
        email : this.email,
        phoneNumber : this.phoneNumber,
        birthDate : this.birthDate + 'T00:00:00.123Z',
        website : this.website,
        biography : this.biography,
        gender : this.gender,
        user : {
          id : this.userId,
          username : this.originalUsername,
          role : 'USER'
        }
      }, {
        headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }
      })
      .then(res => this.openSnackBar('Success', 'Okay'))
      .catch(res => console.log(res));
  }

  buildDate(date): string {
    const year = date[0];
    const month = date[1] < 10 ? '0' + date[1] : date[1];
    const day = date[2] < 10 ? '0' + date[2] : date[2];
    return year + '-' + month + '-' + day;
  }

  validateRequiredFields(): boolean {
    return this.name.trim() !== '' && this.lastName !== '' && this.birthDate.trim() !== '' && this.gender.trim() !== 'Select gender'
      && this.username.trim() !== '';
  }

  validateRegExp(reg: string, toTest: string): boolean {
    const regExp = new RegExp(reg);
    return regExp.test(toTest);
  }

  cancel(): void {
    this.router.navigate(['profile/' + this.username]);
  }

  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
