import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { VerificationRequestsComponent } from '../components/verification-requests/verification-requests.component';
import { FollowRequestDto } from '../DTOs/follow-request-dto';
import { FollowerDto } from '../DTOs/follower-dto';
import { ProfileVerificationRequest } from '../DTOs/profile-verification-request';
import { ProfileInfo } from '../model/profile-info';
import {Profile} from '../model/profile';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private _http : HttpClient) { }

  public getProfileInfo(username : String) : Observable<ProfileInfo> {
    return this._http.get<ProfileInfo>(environment.profileBaseUrl + 'profile/' + username);
  }

  public getProfile(username: string): Observable<Profile> {
    return this._http.get<Profile>(environment.profileBaseUrl + 'profile/one/' + username);
  }

  public editProfileSettings(profile: Profile): Observable<any> {
    return this._http.post(environment.profileBaseUrl + 'profile/update', profile);
  }

  public followProfile(username : string){
    return this._http.get(environment.profileBaseUrl + 'profile/follow/' + username, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public unfollowProfile(username : string) {
    return this._http.get(environment.profileBaseUrl + "profile/unfollow/"+ username, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getFollowRequests(username : String) : Observable<FollowRequestDto[]> {
    return this._http.get<FollowRequestDto[]>(environment.profileBaseUrl + "profile/followRequest/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public acceptRequest(username : String) : Observable<FollowRequestDto[]> {
    return this._http.get<FollowRequestDto[]>(environment.profileBaseUrl + "profile/acceptRequest/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public deleteRequest(username : String) : Observable<FollowRequestDto[]> {
    return this._http.get<FollowRequestDto[]>(environment.profileBaseUrl + "profile/deleteRequest/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getFollowers(username : String){
    return this._http.get<FollowerDto[]>(environment.profileBaseUrl + "profile/followers/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getFollowing(username : String){
    return this._http.get<FollowerDto[]>(environment.profileBaseUrl + "profile/following/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
  public addCloseFriend(data : string) {
    return this._http.post(environment.profileBaseUrl + "profile/addCloseFriend", data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
  public removeCloseFriend(data : string) {
    return this._http.post(environment.profileBaseUrl + "profile/removeCloseFriend", data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
  public getCloseFriends() : Observable<String[]> {
    return this._http.get<String[]>(environment.profileBaseUrl + "profile/getCloseFriends", {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
  public sendProfileVerifactionRequest(fd : FormData){
      return this._http.post(environment.profileBaseUrl + "profile/verify-profile",fd, {responseType: 'text',headers : {
        Authorization: 'Bearer ' + localStorage.getItem('jwt')
      }});

  }

  public getVerificationRequests() : Observable<ProfileVerificationRequest[]> {
    return this._http.get<ProfileVerificationRequest[]>(environment.profileBaseUrl + "profile/verification-requests", {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public acceptVerificationRequest(id : Number) : Observable<ProfileVerificationRequest[]> {
    return this._http.get<ProfileVerificationRequest[]>(environment.profileBaseUrl + "profile/verify/"+ id, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public deleteVerificationRequest(id : Number) : Observable<ProfileVerificationRequest[]> {
    return this._http.get<ProfileVerificationRequest[]>(environment.profileBaseUrl + "profile/delete-ver-request/"+ id, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
}
