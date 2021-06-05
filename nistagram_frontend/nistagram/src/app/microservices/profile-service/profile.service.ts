import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FollowerRequestDialogComponent } from '../components/follower-request-dialog/follower-request-dialog.component';
import { FollowRequestDto } from '../DTOs/follow-request-dto';
import { FollowerDto } from '../DTOs/follower-dto';
import { ProfileInfo } from '../model/profile-info';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private _http : HttpClient) { }

  private profiles : ProfileInfo[] = [
    new ProfileInfo(1, 'pera', 'Pera Peric', 'user bio', new Date(1998, 11, 29), 'email@gmail', 'MALE', '', 150, 194, true, true, false),
    new ProfileInfo(2, 'pera2', 'Pera Peric', 'user bio', new Date(1998, 11, 29), 'email@gmail', 'MALE', '', 150, 194, false, true, false),
    new ProfileInfo(3, 'pera3', 'Pera Peric', 'user bio', new Date(1998, 11, 29), 'email@gmail', 'MALE', '', 150, 194, false, false, false),
    new ProfileInfo(4, 'pera4', 'Pera Peric', 'user bio', new Date(1998, 11, 29), 'email@gmail', 'MALE', '', 150, 194, false, false, true),
    new ProfileInfo(5, 'mika', 'Mika Mikic', 'user bio', new Date(1998, 11, 29), 'email@gmail', 'MALE', '', 150, 194, false, true, true)
  ];
  public getProfileInfo(username : String) {
    const profiles = this.profiles.filter(p => p.username == username)
    if(profiles.length > 0) {
      return profiles[0];
    } else {
      return null;
    }
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
