import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Notification } from '../model/notification';
import { SettingsPerFollow } from '../model/settings-per-follow';
import { SettingsPerProfile } from '../model/settings-per-profile';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http : HttpClient) { }

  public getForProfile() : Observable<SettingsPerProfile> {
    return this.http.get<SettingsPerProfile>(environment.notificationBaseUrl + 'settings/profile');
  }

  public getForFollow() : Observable<SettingsPerFollow[]> {
    return this.http.get<SettingsPerFollow[]>(environment.notificationBaseUrl + 'settings/follow');
  }

  public updatePerFollow(settings : SettingsPerFollow) : Observable<any> {
    return this.http.put(environment.notificationBaseUrl + 'settings/follow', settings);
  }

  public updatePerProfile(settings : SettingsPerProfile) : Observable<any> {
    return this.http.put(environment.notificationBaseUrl + 'settings/profile', settings);
  }

  public getAll() : Observable<Notification[]> {
    return this.http.get<Notification[]>(environment.notificationBaseUrl + 'notification');
  }
}
