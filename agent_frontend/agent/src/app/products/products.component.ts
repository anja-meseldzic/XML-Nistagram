import { Component, OnInit } from '@angular/core';
import {Product} from "../models/product";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] | undefined

  constructor() { }

  ngOnInit(): void {
    this.products = [
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50),
      new Product('', 'T-shirt', 200, 50)
    ]
  }

}
