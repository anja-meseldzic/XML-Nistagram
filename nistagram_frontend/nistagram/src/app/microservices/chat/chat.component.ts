import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth-service/auth.service';
import {environment} from '../../../environments/environment';
import SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  sockJS: any;
  client: any;
  peer: string;
  peerChosen: boolean;
  txtMessage: string;
  messages = [];

  constructor(public authService: AuthService) { }

  ngOnInit(): void {
  this.connect();
  }

  connect = () => {

    const sockJS = new SockJS(environment.messageBaseUrl + '/ws');
    const stompClient = Stomp.over(sockJS);

    this.client = stompClient;

    stompClient.connect({}, () => {
      console.log('LOG: connected');

      stompClient.subscribe(
        '/user/' + this.authService.getUsername() + '/queue/messages',
        (msg) => {
          const message = JSON.parse(msg.body);
          this.messages.push(message);
        }
      );
    }, () => {
      console.log('LOG: Something went wrong...');
    });
  }

  sendMessage = () => {
    if (this.txtMessage.trim() === '') {
      return;
    }

    const sockJS = new SockJS(environment.messageBaseUrl + '/ws');
    const stompClient = Stomp.over(sockJS);

    const message = {
      id: null,
      message: {
        id: null,
        sender: this.authService.getUsername(),
        receiver: this.peer,
      },
      content: this.txtMessage
    };

    this.client.send('/app/chat', {}, JSON.stringify(message));
    this.messages.push(message);
    this.txtMessage = '';
  }

  choosePeer = () => {
    this.peerChosen = true;
  }


}
