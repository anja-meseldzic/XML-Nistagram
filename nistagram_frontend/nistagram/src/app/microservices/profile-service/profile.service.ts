import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
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
}
