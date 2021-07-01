import { Component, OnInit } from '@angular/core';
import {Product} from "../models/product";
import axios from "axios";
import {environment} from "../../environments/environment";
import {AuthServiceService} from "../auth-service/auth-service.service";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: any

  id = null;
  name = '';
  price = 0;
  quantity = 0;
  selectedFile: any = null;


  constructor(public authService: AuthServiceService) { }

  ngOnInit(): void {
    this.fetchData();
  }

  submit() {
    const fd = new FormData();
    if(this.selectedFile != null) {
      fd.append('imageFile', this.selectedFile, this.selectedFile.name);
      fd.append('post', JSON.stringify({
        name: this.name,
        price: this.price,
        quantity: this.quantity
      }));
    }

    axios
      .post(environment.url + 'merch', fd)
      .then(_ => alert('Success'))
      .catch(_ => alert('Error'));
  }

  onFileSelected(event: any): void {
    this.selectedFile = <File> event.target.files[0];
  }

  cancel() {
    this.fetchData()
  }

  fetchData = () => {
    axios
      .get(environment.url + 'merch', {
        headers: {
          Authorization: 'Bearer ' + sessionStorage.getItem('jwt')
        }
      })
      .then(res => {
        this.products = res.data;
        // @ts-ignore
        this.products.forEach(p => p.imagePath = environment.url + p.imagePath)
      })
  }

}
