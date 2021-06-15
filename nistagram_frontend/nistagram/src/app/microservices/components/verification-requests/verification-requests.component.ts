import { Component, OnInit } from '@angular/core';
import { ProfileVerificationRequest } from '../../DTOs/profile-verification-request';
import { ProfileService } from '../../profile-service/profile.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-verification-requests',
  templateUrl: './verification-requests.component.html',
  styleUrls: ['./verification-requests.component.css']
})
export class VerificationRequestsComponent implements OnInit {

  displayedColumns: string[] = ['name', 'surname', 'category', 'picture','action','action1'];
  dataSource : ProfileVerificationRequest[] = [];

  constructor(private profileService: ProfileService) { }

  ngOnInit(): void {
    this.profileService.getVerificationRequests().subscribe(data => {
      for(var r of data){
        r.url = environment.mediaBaseUrl + r.url;
        console.log(r.url);
      }
      this.dataSource = data;})
  }

  verify(id : Number){
    this.profileService.acceptVerificationRequest(id).subscribe(data =>{
      this.dataSource = data;
    })
  }
  delete(id: Number){
    this.profileService.deleteVerificationRequest(id).subscribe(data =>{
      this.dataSource = data;
    })
  }

}
