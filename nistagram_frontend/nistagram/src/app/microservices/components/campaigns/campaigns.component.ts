import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from 'src/environments/environment';
import { CampaignService } from '../../campaign-service/campaign.service';
import { Campaign } from '../../model/campaign';
import { CampaignDetails } from '../../model/campaign-details';

@Component({
  selector: 'app-campaigns',
  templateUrl: './campaigns.component.html',
  styleUrls: ['./campaigns.component.css']
})
export class CampaignsComponent implements OnInit {

  constructor(private campService : CampaignService, private snackBar : MatSnackBar) { }

  public campaigns : Campaign[] = []

  ngOnInit(): void {
    this.campService.get().subscribe(
      res => { this.campaigns = res; this.getMediaUrls(); }
    )
  }

  getMediaUrls() {
    for(let c of this.campaigns) {
      this.campService.getMediaPath(c.mediaId).subscribe(
        res => { 
          c['path'] = res != null ? environment.mediaBaseUrl + res : null;
          c['ageStrings'] = []
          c.start = new Date(c.start[0], c.start[1]-1, c.start[2], c.start[3], c.start[4])
          if(c.details != null) {
            c.details.endDate = new Date(c.details.endDate[0], c.details.endDate[1]-1, c.details.endDate[2], c.details.endDate[3], c.details.endDate[4])
          }
          for(let a of c.targetedAges) {
            this.campService.getAge(a).subscribe(
              res => {
                if(res != null) {
                  c['ageStrings'].push(res.minAge + " - " + res.maxAge)
                }
              }
            )
          }
        }
      )
    }

  }

  repeated(c : Campaign) {
    return c.details != null
  }

  delete(id : number) {
    this.campService.delete(id).subscribe(
      res => {
        this.campService.get().subscribe(
          res => { this.campaigns = res; this.getMediaUrls(); }
        )
      }
    )
  }

  update(id: number, dto: CampaignDetails) {
    dto.endDate.setHours(dto.endDate.getHours() + 2)
    this.campService.update(id, dto).subscribe(
      _ => {
        this.openSnackBar("Update successful! Changes will be applicable after 24 hours")
        this.campService.get().subscribe(
          res => { this.campaigns = res; this.getMediaUrls(); }
        )
      },
      error => this.openSnackBar(error.error.message)
    )
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }

  disabled(c : Campaign) {
    if(c.start > c.details.endDate)
      return true;
    if(c.details.timesPerDay <= 0)
      return true;
    return false;
  }

  dateChanged(event, id : number) {
    console.log(id)
    this.campaigns.forEach(c => { if(c.id == id) {c.details.endDate = event.target.value} })
  }
}
