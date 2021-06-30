import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";
import axios from "axios";
import {AuthServiceService} from "../auth-service/auth-service.service";

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  registerUserUrl = environment.url + "users/"
  loginUrl = environment.url + 'users/login'

  constructor(private http : HttpClient, private authService: AuthServiceService) { }

  register(user: User): Observable<any> {
    return this.http.post(this.registerUserUrl, user);
  }

  login(username: string, password: string): string {
    let jwt = '';
    axios
      .post(this.loginUrl, {
        username,
        password
      })
      .then(res => this.authService.saveToken(res.data))
      .catch(_ => alert('Wrong username or password'))
    return jwt;

    // return this.http.post(this.loginUrl, {
    //   username,
    //   password
    // })
  }
}
