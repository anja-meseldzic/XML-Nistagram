import { Component } from '@angular/core';
import {AuthServiceService} from "./auth-service/auth-service.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'agent';

  constructor(public authService: AuthServiceService) {
  }
}
