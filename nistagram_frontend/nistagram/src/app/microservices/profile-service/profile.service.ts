import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { FollowRequestDto } from '../DTOs/follow-request-dto';
import { FollowerDto } from '../DTOs/follower-dto';
import { ProfileInfo } from '../model/profile-info';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private _http : HttpClient) { }

  public getProfileInfo(username : String) : Observable<ProfileInfo> {
    return this._http.get<ProfileInfo>(environment.profileBaseUrl + 'profile/' + username);
  }

  public followProfile(username : string){
    return this._http.get("http://localhost:8085/profile/follow/"+ username, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public unfollowProfile(username : string) {
    return this._http.get("http://localhost:8085/profile/unfollow/"+ username, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getFollowRequests(username : String) : Observable<FollowRequestDto[]> {
    return this._http.get<FollowRequestDto[]>("http://localhost:8085/profile/followRequest/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public acceptRequest(username : String) : Observable<FollowRequestDto[]> {
    return this._http.get<FollowRequestDto[]>("http://localhost:8085/profile/acceptRequest/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public deleteRequest(username : String) : Observable<FollowRequestDto[]> {
    return this._http.get<FollowRequestDto[]>("http://localhost:8085/profile/deleteRequest/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getFollowers(username : String){
    return this._http.get<FollowerDto[]>("http://localhost:8085/profile/followers/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getFollowing(username : String){
    return this._http.get<FollowerDto[]>("http://localhost:8085/profile/following/"+ username, {headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
}
