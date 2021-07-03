import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../auth-service/auth.service';
import { AgentRequestDTO } from '../../DTOs/agent-request-dto';

@Component({
  selector: 'app-agent-requests',
  templateUrl: './agent-requests.component.html',
  styleUrls: ['./agent-requests.component.css']
})
export class AgentRequestsComponent implements OnInit {

  displayedColumns: string[] = ['name', 'surname', 'website', 'email','accept','reject'];
  dataSource : AgentRequestDTO[] = [];

  constructor( private snackBar : MatSnackBar, private authService : AuthService) { }

  ngOnInit(): void {
    this.getAllRequests();
  }

  public getAllRequests(){
    this.authService.getAllAgentRequests().subscribe(
      data => { this.dataSource = data; },
      error => this.openSnackBar(error.error.message)
    );
  }

  reject(id : number){

  }

  accept(id : number){

  }
  openSnackBar(message: string) {
    this.snackBar.open(message, "Okay", {
      duration: 5000,
    });
  }
}
