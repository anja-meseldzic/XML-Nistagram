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
  selectedFile: any = null;
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
          message.linkToSource = environment.messageBaseUrl + message.linkToSource;
          console.log(message);
          this.messages.push(message);
        }
      );
    }, () => {
      console.log('LOG: Something went wrong...');
    });
  }

  sendMessage = () => {
    if (this.selectedFile == null) {
      this.sendTextMessage();
    }
    else {
      this.sendOneTimeMessage();
    }
    this.selectedFile = null;
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

  sendOneTimeMessage = () => {
    const fd = new FormData();
    const message = {
      id: null,
      sender: this.authService.getUsername(),
      receiver: this.peer,
      type: 'ONETIME',
      content: this.txtMessage,
      active: true,
      seen: false
    };
    if(this.selectedFile != null) {
      fd.append('imageFile', this.selectedFile, this.selectedFile.name);
      fd.append('post', JSON.stringify(message));
    }


    axios
      .post(environment.messageBaseUrl + 'messages', fd, {
        headers: {
          Authorization: 'Bearer ' + sessionStorage.getItem('jwt')
        }
      })
      // @ts-ignore
      .then(res => message.linkToSource = environment.messageBaseUrl + res.data);

    const d = new Date();
    // @ts-ignore
    message.date = {
      hour: d.getHours(),
      minute: d.getMinutes(),
      month: this.months[d.getMonth()],
      dayOfMonth: d.getDate()
    };
    this.messages.push(message);
  }

  choosePeer = () => {
    this.peerChosen = true;
    axios
      .get(environment.messageBaseUrl + 'messages/' + this.authService.getUsername() + '/' + this.peer, {
        headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }
      })
      .then(res => {
        console.log(res.data);
        res.data.forEach(m => {
          m.date = {
            hour: m.date[3],
            minute: m.date[4],
            month: this.months[m.date[1] - 1],
            dayOfMonth: m.date[2],
            nano: new Date(m.date[0], m.date[1], m.date[2], m.date[3], m.date[4], m.date[5]).getTime()
          };
          m.linkToSource = environment.messageBaseUrl + m.linkToSource;
        });
        this.messages = res.data.sort((a, b) => (a.date.nano > b.date.nano) ? 1 : ((a.date.nano < b.date.nano) ? -1 : 0));;
      });
  }

  onFileSelected(event: any): void {
    this.selectedFile = <File> event.target.files[0];
  }

  seeMessage = (type, id) => {
    if (type !== 'ONETIME' || id == null) {
      return;
    }

    axios
      .put(environment.messageBaseUrl + 'messages/' + id, {
        headers : {
          Authorization: 'Bearer ' + localStorage.getItem('jwt')
        }
      })
      .then(_ => this.messages = this.messages.filter(m => m.id !== id));
  }


}
