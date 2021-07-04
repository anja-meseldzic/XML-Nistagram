import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AgeGroup } from '../model/age-group';
import { Campaign } from '../model/campaign';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {

  constructor(private http : HttpClient) { }

  public getAges() : Observable<AgeGroup[]> {
    return this.http.get<AgeGroup[]>(environment.campaignBaseUrl + 'target-group/age')
  }

  public getGenders() : Observable<string[]> {
    return this.http.get<string[]>(environment.campaignBaseUrl + 'target-group/gender')
  }

  public create(c : Campaign) : Observable<any> {
    return this.http.post(environment.campaignBaseUrl + 'campaign', c)
  }
}
