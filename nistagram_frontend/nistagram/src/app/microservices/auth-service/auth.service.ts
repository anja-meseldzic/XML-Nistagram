import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import jwtDecode from 'jwt-decode';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  logOut(): boolean {
    if (!this.isLoggedIn()) { return false; }
    localStorage.removeItem('jwt');
    return true;
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
}
