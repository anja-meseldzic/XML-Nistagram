import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth-service/auth.service';
import {environment} from '../../../environments/environment';
import SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';
import axios from 'axios';

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
  months = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUN', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];


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
          console.log(message);
          this.messages.push(message);
        }
      );
    }, () => {
      console.log('LOG: Something went wrong...');
    });
  }

  sendTextMessage = () => {
    if (this.txtMessage.trim() === '') {
      return;
    }

    const message = {
      id: null,
      sender: this.authService.getUsername(),
      receiver: this.peer,
      type: 'TEXT',
      content: this.txtMessage,
      active: true
    };

    this.client.send('/app/chat', {}, JSON.stringify(message));
    const d = new Date();
    // @ts-ignore
    message.date = {
      hour: d.getHours(),
      minute: d.getMinutes(),
      month: this.months[d.getMonth()],
      dayOfMonth: d.getDate()
    };

    this.messages.push(message);
    this.txtMessage = '';
  }

  choosePeer = () => {
    this.peerChosen = true;
    axios
      .get(environment.messageBaseUrl + 'messages/' + this.peer, {
        headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }
      })
      .then(res => {
        console.log(res.data);
        res.data.forEach(m => m.date = {
          hour: m.date[3],
          minute: m.date[4],
          month: this.months[m.date[1] - 1],
          dayOfMonth: m.date[2],
          nano: m.date[6]
        });
        this.messages = res.data.sort((a, b) => (a.date.nano > b.date.nano) ? 1 : ((a.date.nano < b.date.nano) ? -1 : 0));;
      });
  }


}
