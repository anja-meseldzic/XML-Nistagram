import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AllCommentDTO } from '../DTOs/all-comment-dto';
import { AllReactionsDTO } from '../DTOs/all-reactions-dto';
import { CollectionDTO } from '../DTOs/collection-dto';
import { CollectionInfoDto } from '../DTOs/collection-info-dto';
import { CommentDTO } from '../DTOs/comment-dto';
import { RatingDTO } from '../DTOs/rating-dto';
import { ReactionsNumberDTO } from '../DTOs/reactions-number-dto';
import { Post } from '../model/post';
import { SearchResult } from '../model/search-result';
import { Story } from '../model/story';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private postDataUrl = environment.mediaBaseUrl + "media/createPost";
  private createStoryUrl = environment.mediaBaseUrl + "media/createStory";
  private createAlbumUrl = environment.mediaBaseUrl + "media/createAlbum";
  private postCommentUrl = environment.mediaBaseUrl + "media/postComment";
  private getAllCommentsUrl = environment.mediaBaseUrl + "media/allComments";
  private reactOnPostUrl = environment.mediaBaseUrl + "media/reactOnPost";
  private getReactionsNumberUrl = environment.mediaBaseUrl + "media/getReactionsNumber";
  private getReactionsUrl = environment.mediaBaseUrl + "media/allReactions";

  constructor(private _http : HttpClient) {}

  getAllReactions(data : number) : Observable<AllReactionsDTO>{
    return this._http.post<AllReactionsDTO>(this.getReactionsUrl, data, {responseType: 'json',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  getReactionsNumber(data : number) : Observable<ReactionsNumberDTO>{
    return this._http.post<ReactionsNumberDTO>(this.getReactionsNumberUrl, data, {responseType: 'json',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  reactOnPost(data : RatingDTO) {
    return this._http.post(this.reactOnPostUrl, data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
  
  getComments(data : number) : Observable<AllCommentDTO[]>{
    return this._http.post<AllCommentDTO[]>(this.getAllCommentsUrl, data, {responseType: 'json',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  postComment(data : CommentDTO) {
    return this._http.post(this.postCommentUrl,data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  postData(data : FormData) {
    return this._http.post(this.postDataUrl,data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  postStory(data : FormData) {
    return this._http.post(this.createStoryUrl, data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  postAlbum(data : FormData) {
    return this._http.post(this.createAlbumUrl, data, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
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

  public saveToFavourites(id : Number){
    return this._http.get(environment.mediaBaseUrl + 'post/saveFavourite/' + id, {responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }})
  }

  public getFavouritesForUser() : Observable<Post[]> {
    return this._http.get<Post[]>(environment.mediaBaseUrl + 'post/favourites');
  }

  public getStoriesForUser() : Observable<Story[]> {
    return this._http.get<Story[]>(environment.mediaBaseUrl + 'story/allStories');
  }

  public getHighlights() : Observable<Story[]> {
    return this._http.get<Story[]>(environment.mediaBaseUrl + 'story/storyHighlights');
  }

  public saveToHighlights(story : Story){
    return this._http.post(environment.mediaBaseUrl + 'story/saveToHighlights', story,{responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public addToCollection(dto : CollectionDTO){
    return this._http.post(environment.mediaBaseUrl + 'post/addToCollection', dto,{responseType: 'text',headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }

  public getCollections(): Observable<CollectionInfoDto[]>{
    return this._http.get<CollectionInfoDto[]>(environment.mediaBaseUrl + 'post/collections',{headers : {
      Authorization: 'Bearer ' + localStorage.getItem('jwt')
    }});
  }
}
