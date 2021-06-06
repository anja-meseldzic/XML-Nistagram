import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import jwtDecode from 'jwt-decode';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private router : Router) { }

  logOut(){
    localStorage.removeItem('jwt');
    this.router.navigate(['./']);
  }

  // setLoggedIn(token: string) {
  //   localStorage.setItem('token', token);
  // }

  isLoggedIn(): boolean {
    return localStorage.getItem('jwt') != null;
  }

  getRole(): string {
    if (!this.isLoggedIn()) { return ''; }
    const token = localStorage.getItem('jwt');
    const tokenPayload = jwtDecode(token);
    // @ts-ignore
    return tokenPayload.role;
  }

  getUsername(): string {
    if (!this.isLoggedIn()) { return ''; }
    const token = localStorage.getItem('jwt');
    const tokenPayload = jwtDecode(token);
    // @ts-ignore
    return tokenPayload.username;
  }
}
