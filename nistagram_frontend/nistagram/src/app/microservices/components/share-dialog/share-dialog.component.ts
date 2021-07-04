import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../auth-service/auth.service';
import axios from 'axios';
import {environment} from '../../../../environments/environment';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Component({
  selector: 'app-share-dialog',
  templateUrl: './share-dialog.component.html',
  styleUrls: ['./share-dialog.component.css']
})
export class ShareDialogComponent implements OnInit {

  peer: '';
  client: any;

  constructor(private authService: AuthService, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    const sockJS = new SockJS(environment.messageBaseUrl + '/ws');
    const stompClient = Stomp.over(sockJS);

    this.client = stompClient;

    stompClient.connect({}, () => {
      console.log('LOG: connected');
    }, () => {
      console.log('LOG: Something went wrong...');
    });
  }

  submit = () => {
      const message = {
        id: null,
        sender: this.authService.getUsername(),
        receiver: this.peer,
        type: 'LINK',
        content: window.location.href,
        active: true
      };

      this.client.send('/app/chat', {}, JSON.stringify(message));
    // axios
      //   .post(environment.messageBaseUrl + 'messages/share', message, {
      //     headers : {
      //       Authorization: 'Bearer ' + localStorage.getItem('jwt')
      //     }
      //   })
      //   .then(_ => this.openSnackBar('Successfully shared!', 'Okay'));
  }

  openSnackBar = (message: string, action: string) => {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }
}
