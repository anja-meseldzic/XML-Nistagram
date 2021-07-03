import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import jwtDecode from 'jwt-decode';
import { Router } from '@angular/router';
import { AgentDTO } from '../DTOs/agent-dto';
import {environment} from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private sendAgentRequestUrl = environment.authBaseUrl + "agent/agentRegistrationRequest";

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

  public sendAgentRegistrationRequest(data : AgentDTO) {
    return this.http.post(this.sendAgentRequestUrl, data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
}
