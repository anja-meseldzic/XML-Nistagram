import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AllCommentDTO } from '../DTOs/all-comment-dto';
import { CommentDTO } from '../DTOs/comment-dto';
import { Post } from '../model/post';
import { SearchResult } from '../model/search-result';
import { Story } from '../model/story';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private postDataUrl = environment.mediaBaseUrl + "/media/createPost";
  private createStoryUrl = environment.mediaBaseUrl + "/media/createStory";
  private createAlbumUrl = environment.mediaBaseUrl + "/media/createAlbum";
  private postCommentUrl = environment.mediaBaseUrl + "/media/postComment";
  private getAllCommentsUrl = environment.mediaBaseUrl + "/media/allComments";

  constructor(private _http : HttpClient) { }

  getComments(data : number) : Observable<AllCommentDTO[]>{
    return this._http.post<AllCommentDTO[]>(this.getAllCommentsUrl, data, {responseType: 'json'});
  }

  postComment(data : CommentDTO) {
    return this._http.post(this.postCommentUrl,data, {responseType: 'text'});
  }

  postData(data : FormData) {
    return this._http.post(this.postDataUrl,data, {responseType: 'text'});
  }

  postStory(data : FormData) {
    return this._http.post(this.createStoryUrl, data, {responseType: 'text'});
  }

  postAlbum(data : FormData) {
    return this._http.post(this.createAlbumUrl, data, {responseType: 'text'});
  }

  public getPostsForFeed() : Observable<Post[]> {
    return this._http.get<Post[]>(environment.mediaBaseUrl + 'post/feed');
  }

  public getStoriesForFeed() : Observable<Story[]> {
    return this._http.get<Story[]>(environment.mediaBaseUrl + 'story/feed');
  }

  public getSearchResults(input : String) : Observable<SearchResult[]> {
    return this._http.get<SearchResult[]>(environment.mediaBaseUrl + 'post/search/' + input);
  }

  public getPostsBySearchCriteria(input : SearchResult) : Observable<Post[]> {
    return this._http.get<Post[]>(environment.mediaBaseUrl + 'post/' + input.type + '/' + input.name);
  }

  public getPost(id : String) : Observable<Post> {
    return this._http.get<Post>(environment.mediaBaseUrl + 'post/' + id);
  }

  public getPostsByUser(username : String) : Observable<Post[]> {
    return this._http.get<Post[]>(environment.mediaBaseUrl + 'post/profile/' + username);
  }

  public getStoriesByUser(username : String) : Observable<Story[]> {
    return this._http.get<Story[]>(environment.mediaBaseUrl + 'story/profile/' + username);
  }
}
