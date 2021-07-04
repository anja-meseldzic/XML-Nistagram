import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from 'process';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AgeGroup } from '../model/age-group';
import { Campaign } from '../model/campaign';
import { CampaignDetails } from '../model/campaign-details';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {

  constructor(private http : HttpClient) { }

  public getAges() : Observable<AgeGroup[]> {
    return this.http.get<AgeGroup[]>(environment.campaignBaseUrl + 'target-group/age')
  }

  public getAge(id : number) : Observable<AgeGroup> {
    return this.http.get<AgeGroup>(environment.campaignBaseUrl + 'target-group/age/' + id)
  }

  public getGenders() : Observable<string[]> {
    return this.http.get<string[]>(environment.campaignBaseUrl + 'target-group/gender')
  }

  public create(c : Campaign) : Observable<any> {
    return this.http.post(environment.campaignBaseUrl + 'campaign', c)
  }

  public get() : Observable<Campaign[]> {
    return this.http.get<Campaign[]>(environment.campaignBaseUrl + 'campaign')
  }

  public getMediaPath(id : number) : Observable<string> {
    return this.http.get(environment.mediaBaseUrl + 'media/path/' + id, {responseType : 'text'})
  }

  public delete(id : number) {
    return this.http.delete(environment.campaignBaseUrl + 'campaign/' + id)
  }

  public update(id: number, dto: CampaignDetails) {
    return this.http.put(environment.campaignBaseUrl + 'campaign/' + id, dto)
  }
}
