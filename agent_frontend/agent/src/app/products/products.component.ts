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
        id: this.id,
        name: this.name,
        price: this.price,
        quantity: this.quantity
      }));
    }

    axios
      .post(environment.url + 'merch', fd)
      .then(_ => {
        alert('Success');
        this.id = null;
      })
      .catch(_ => {
        alert('All fields are required')
      });

  }

  onFileSelected(event: any): void {
    this.selectedFile = <File> event.target.files[0];
  }

  cancel() {
    this.id = null;
    this.fetchData()
  }

  restart() {
    this.id = null;
    this.name = '';
    this.quantity = 0;
    this.price = 0;
  }

  delete(id: any) {
    axios
      .delete(environment.url + 'merch/' + id)
      .then(_ => {
        alert('success');
        this.fetchData()
      })
      .catch(_ => alert('error'))
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

  update = (id: any) => {
    // @ts-ignore
    let product = this.products.filter(p => p.id === id)[0];
    this.name = product.name;
    this.price = product.price;
    this.quantity = product.quantity;
    this.id = product.id;
  }

  buy(id: any): void {
    axios
      .get(environment.url + 'purchase/buy/' + this.authService.getUsername() + '/' + id)
      .then(_ => this.fetchData())
      .catch(_ => alert('No more available product'))
  }

}
