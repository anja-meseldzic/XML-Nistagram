import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CampaignService } from '../../campaign-service/campaign.service';
import { AlbumDTO } from '../../DTOs/album-dto';
import { MediaService } from '../../media-service/media.service';
import { Campaign } from '../../model/campaign';
import { CampaignDetails } from '../../model/campaign-details';

@Component({
  selector: 'app-new-campaign',
  templateUrl: './new-campaign.component.html',
  styleUrls: ['./new-campaign.component.css']
})
export class NewCampaignComponent implements OnInit {

  constructor(private campService : CampaignService, private mediaService : MediaService, private snackBar : MatSnackBar) { }

  public postSelected = true;

  public selectedFile: File = null;
  fileExtension = null;

  public link = "";
  public ages = new FormControl();
  public ageList = [];
  public genders = new FormControl();
  public genderList = [];

  public description : string = "";
  public location : string = "";
  public tag : string = "";
  public dataSource: string[] = [];
  displayedColumns: string[] = ['tag'];

  public repeated = false;
  public date = new FormControl(new Date());
  public time : string = new Date().getHours + ":" + new Date().getMinutes;
  public range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });
  public timesPerDay = 1;

  ngOnInit(): void {
    this.getTargetGroups()
  }

  getTargetGroups() {
    this.campService.getAges().subscribe(
      res => {
        this.ageList = res
        this.campService.getGenders().subscribe(
          res2 => this.genderList = res2
        )
      }
    )
  }

  changeMediaType($event: MatRadioChange) {
    if ($event.value == 0) {
      this.postSelected = true;
    }else {
      this.postSelected = false;
    }
  }

  changeCampaignType($event: MatRadioChange) {
    if ($event.value == 0) {
      this.repeated = false;
    }else {
      this.repeated = true;
    }
  }

  onFileSelected(event) {
    this.selectedFile = <File>event.target.files[0];
    this.fileExtension = this.selectedFile.name.split('?')[0].split('.').pop();
  }

  add() {
    this.postMediaContent().subscribe(
      mediaId => this.createCampaign(mediaId),
      error => this.openSnackBar(error.error.message)
    )
  }

  postMediaContent() {
    const fd = new FormData();
    fd.append('file', this.selectedFile, this.selectedFile.name);
    var album =  new AlbumDTO(this.postSelected, false, this.dataSource,this.location, this.description);
    fd.append('album', JSON.stringify(album));
    return this.mediaService.postAlbum(fd)
  }

  createCampaign(mediaId : number) {
    var details : CampaignDetails = null;
    if(this.repeated) {
      var end : Date = this.range.value.end
      end.setHours(end.getHours() + 2)
      details = new CampaignDetails(this.range.value.end, this.timesPerDay)
    }
    var start : Date = this.repeated ? this.range.value.start : this.date.value
    start.setHours(start.getHours() + 2)
    start.setHours(Number(this.time.split(":")[0]) + 2)
    start.setMinutes(Number(this.time.split(":")[1]))
    var campaign = new Campaign(1, mediaId, this.link, start, this.genders.value, this.ages.value, details)
    this.campService.create(campaign).subscribe(
      _ => this.openSnackBar("Campaign is successfully created"),
      error => this.openSnackBar(error.error.message)
    )
  }

  linkValid() {
    return this.link.length > 0;
  }

  addTag(){
    if(this.tag == ""){
      return;
    }
    this.dataSource.push(this.tag);
    var dataSource2 = this.dataSource;
    this.dataSource= [];
    for (let i=0; i<dataSource2.length; i++){
      this.dataSource.push(dataSource2[i]);
    }
    this.tag = ""  
  }

  submitDisabled() {
    if(this.selectedFile == null)
      return true;
    if(this.link.length == 0)
      return true;
    if((this.ages.value == null || this.ages.value.length == 0) || (this.genders.value == null || this.genders.value.length == 0))
      return true;
    if(this.repeated && this.range.value.start < new Date())
      return true;
    if(this.repeated && this.timesPerDay <= 0)
      return true;
    if(!this.repeated) {
      var d = new Date(this.date.value)
      const h : number = Number(this.time.split(":")[0])
      const m : number = Number(this.time.split(":")[1])
      d.setHours(h)
      d.setMinutes(m)
      if(d < new Date())
        return true;
    }
    return false;
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }
}
