import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import jwtDecode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(private http: HttpClient, private router : Router) { }

  saveToken(jwt: string) {
    sessionStorage.setItem('jwt', jwt)
  }

  logOut(){
    sessionStorage.removeItem('jwt');
    this.router.navigate(['./']);
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('jwt') != null;
  }

  getRole(): string {
    if (!this.isLoggedIn()) { return ''; }
    const token = sessionStorage.getItem('jwt');
    let tokenPayload;
    if (typeof token === "string") {
      tokenPayload = jwtDecode(token);
    }
    // @ts-ignore
    return tokenPayload.role;
  }

  getUsername(): string {
    if (!this.isLoggedIn()) { return ''; }
    const token = sessionStorage.getItem('jwt');
    let tokenPayload;
    if (typeof token === "string") {
      tokenPayload = jwtDecode(token);
    }
    // @ts-ignore
    return tokenPayload.username;
  }
}
