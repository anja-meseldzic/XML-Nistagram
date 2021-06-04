import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { MatSnackBar } from '@angular/material/snack-bar';
import jwtDecode from 'jwt-decode';


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

  // tslint:disable-next-line:variable-name
  constructor(private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const jwt = localStorage.getItem('jwt');
    const jwtDec = jwtDecode(jwt);

    axios
      // @ts-ignore
      .get('http://localhost:8081/regulars/' + jwtDec.id, {
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
        console.log(this.birthDate);
      });
  }

  edit(): void {
    axios
      .post('http://localhost:8081/regulars/update', {
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
          username : this.username,
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

  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
