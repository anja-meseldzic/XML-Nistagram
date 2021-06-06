import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import axios from 'axios';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';

  constructor(private router: Router, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  login(): void {
    if (!this.validate()) {
      this.openSnackBar('Username and password are required', 'Okay');
      return;
    }

    axios
      .post('http://localhost:8081/auth', {
        username : this.username,
        password : this.password
      },)
      .then(res => {
        localStorage.setItem('jwt', res.data);
        this.router.navigate(['./feed']);
      });
  }

  validate(): boolean {
    return this.username.trim() !== '' && this.password.trim() !== '';
  }

  cancel(): void {
    this.router.navigate(['/']);
  }

  openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
