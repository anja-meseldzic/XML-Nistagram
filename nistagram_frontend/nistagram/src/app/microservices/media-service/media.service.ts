import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostDTO } from '../DTOs/post-dto';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private baseUrl = "http://localhost:8083";
  private postDataUrl = this.baseUrl + "/media/createPost";

  constructor(private _http : HttpClient) { }

  postData(data : FormData) {
    return this._http.post(this.postDataUrl,data, {responseType: 'text'});
  }
}
