import { Component, OnInit } from '@angular/core';
import axios from "axios";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

  urls : any[] = []

  constructor() { }

  ngOnInit(): void {
    axios
      .get(environment.url + 'report/resources', {
        headers: {
          Authorization: 'Bearer ' + sessionStorage.getItem('jwt')
        }
      })
      .then(res => {
        console.log(res.data)
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(res.data, 'text/html');
        const nodes = xmlDoc.getElementsByTagName('exist:value');
        for(let i = 0; i < nodes.length; i++) {
          // @ts-ignore
          this.urls.push({
            // @ts-ignore
            title: this.xmlToPdf(nodes.item(i).innerHTML),
            // @ts-ignore
            dest: environment.url + 'merch/content/' + this.xmlToPdf(nodes.item(i).innerHTML)
          })
        }
      })
  }

  xmlToPdf = (title: string) => {
    if(title.includes('.')) {
      const parts = title.split('.');
      return parts[0] + '.pdf';
    }
    return title;
  }

  generateNewReport = () => {
    axios
      .get(environment.url + 'report', {
        headers: {
          Authorization: 'Bearer ' + sessionStorage.getItem('jwt')
        }
      })
      .then(res => {
        const parts = res.data.split('/');
        this.urls.push({
          title: this.xmlToPdf(parts[parts.length - 1]),
          dest: environment.url + this.xmlToPdf(res.data)
        })
      })

  }

}
