import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { CampaignService } from '../../campaign-service/campaign.service';
import { MediaService } from '../../media-service/media.service';
import { Campaign } from '../../model/campaign';

@Component({
  selector: 'app-influencer-campaign-dialog',
  templateUrl: './influencer-campaign-dialog.component.html',
  styleUrls: ['./influencer-campaign-dialog.component.css']
})
export class InfluencerCampaignDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data : Campaign[], private mediaService : MediaService, private campService : CampaignService, public snackBar : MatSnackBar) { }

  public campaigns : Campaign[] = []
  public influencersList = [];

  ngOnInit(): void {
    this.campaigns = this.data;
    this.getMediaUrls();
  }

  getMediaUrls() {
    for(let c of this.campaigns) {
      this.campService.getMediaPath(c.mediaId).subscribe(
        res => { 
          c['path'] = res != null ? environment.mediaBaseUrl + res : null;
        }
      )
    }

  }

  repeated(c : Campaign) {
    return c.details != null
  }

  delete(campaign : Campaign) {
    for(let c of this.campaigns){
        if(c.id == campaign.id){
          let index = this.campaigns.indexOf(c);
          this.campaigns.splice(index,1);
        }
    }
    this.snackBar.open("You have successfully deleted this campaign.", "Okay");
    this.campService.denyCampaign(campaign).subscribe();
  }

  accept(campaign : Campaign){
    for(let c of this.campaigns){
      if(c.id == campaign.id){
        let index = this.campaigns.indexOf(c);
        this.campaigns.splice(index,1);
      }
    }
    console.log(campaign.mediaId);
    this.mediaService.createNewMedia(campaign.mediaId).subscribe(data => campaign.mediaId = Number(data));
    console.log(campaign.mediaId);
    this.snackBar.open("You have successfully accepted this campaign.", "Okay");
    this.campService.acceptCampaign(campaign).subscribe();
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }
}
