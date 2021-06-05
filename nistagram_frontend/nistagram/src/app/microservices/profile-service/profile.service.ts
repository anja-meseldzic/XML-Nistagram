import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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
}
