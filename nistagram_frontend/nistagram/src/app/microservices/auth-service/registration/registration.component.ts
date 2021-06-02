import { Component, OnInit } from '@angular/core';
import axios from "axios";

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

  constructor() { }

  ngOnInit(): void {
  }

  register(): void {
    const genders = ['MALE', 'FEMALE', 'OTHER']

    axios
      .post('http://localhost:8080/register', {
        name : this.name,
        'last-name' : this.lastName,
        email : this.email,
        'phone-number' : this.phoneNumber,
        'date-of-birth' : this.birthDate + 'T00:00:00.123Z',
        website : this.website,
        biography : this.biography,
        gender : genders.indexOf(this.gender),
        user : {
          username : this.username,
          password : this.password,
          role : 0
        }
      })
      .then(res => console.log('success'));
  }

}
