import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import axios from 'axios';

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
    axios
      .post('http://localhost:8081/auth', {
        username : this.username,
        password : this.password
      })
      .then(res => {
        localStorage.setItem('jwt', res.data);
      });
  }

  cancel(): void {
    this.router.navigate(['/']);
  }
}
