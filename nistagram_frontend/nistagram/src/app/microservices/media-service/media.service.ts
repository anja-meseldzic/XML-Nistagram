import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AllCommentDTO } from '../DTOs/all-comment-dto';
import { AllReactionsDTO } from '../DTOs/all-reactions-dto';
import { CommentDTO } from '../DTOs/comment-dto';
import { PostDTO } from '../DTOs/post-dto';
import { RatingDTO } from '../DTOs/rating-dto';
import { ReactionsNumberDTO } from '../DTOs/reactions-number-dto';
import { Post } from '../model/post';
import { SearchResult } from '../model/search-result';
import { Story } from '../model/story';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private baseUrl = "http://localhost:8083";
  private postDataUrl = this.baseUrl + "/media/createPost";
  private createStoryUrl = this.baseUrl + "/media/createStory";
  private createAlbumUrl = this.baseUrl + "/media/createAlbum";
  private postCommentUrl = this.baseUrl + "/media/postComment";
  private getAllCommentsUrl = this.baseUrl + "/media/allComments";
  private reactOnPostUrl = this.baseUrl + "/media/reactOnPost";
  private getReactionsNumberUrl = this.baseUrl + "/media/getReactionsNumber";
  private getReactionsUrl = this.baseUrl + "/media/allReactions";

  constructor(private _http : HttpClient) { }

  private posts : Post[] = [
    new Post(0, 'pera', ['https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg', 'https://www.youtube.com/watch?v=U_O1QKQCsGs'], '', 'description', ['#tag'], new Date(2021, 6, 3, 9, 15, 0)),
    new Post(1, 'pera', ['https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg'], 'location', 'description', ['#tag'], new Date(2021, 6, 3, 8, 15, 0)),
    new Post(2, 'mika', ['https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg'], '', 'description', ['#tag'], new Date(2021, 6, 3, 9, 15, 0)),
    new Post(3, 'laza', ['https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg', 'https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg'], '', 'description', ['#tag'], new Date(2021, 6, 3, 9, 15, 0))
  ];

  private stories : Story[] = [
    new Story('pera', 'https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg', new Date(2021, 6, 3, 9, 15, 0)),
    new Story('pera', 'https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg', new Date(2021, 6, 2, 22, 15, 0)),
    new Story('pera', 'https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg', new Date(2021, 6, 3, 7, 15, 0)),
    new Story('mika', 'https://www.studyinserbia.rs/uploads/attachment/strana/228/large_tara-mountain-5520592_1920.jpg', new Date(2021, 6, 3, 9, 15, 0)),
    new Story('mika', 'https://www.youtube.com/watch?v=U_O1QKQCsGs', new Date(2021, 6, 3, 10, 15, 0))
  ];

  private searchResults : SearchResult[] = [
    new SearchResult('pera', 'profile'),
    new SearchResult('mika', 'profile'),
    new SearchResult('tag', 'hashtag'),
    new SearchResult('location', 'location')
  ];

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

  public getPostsForFeed() : Post[] {
    return this.posts;
  }

  public getStoriesForFeed() : Story[] {
    return this.stories;
  }

  public getSearchResults(input : String) : SearchResult[] {
    return this.searchResults.filter(sr => sr.name.toLowerCase().includes(input.toLowerCase()));
  }

  public getPostsBySearchCriteria(input : SearchResult) : Post[] {
    if(input.type == 'location') {
      return this.posts.filter(p => p.location === input.name);
    }
    else if (input.type == 'hashtag') {
      return this.posts.filter(p => p.hashtags.includes('#' + input.name))
    } else {
      return null;
    }
  }

  public getPost(id : String) : Post {
    const posts = this.posts.filter(p => p.id.toString() == id);
    if(posts.length > 0) {
      return posts[0];
    } else {
      return null;
    }
  }

  public getPostsByUser(username : String) : Post[] {
    return this.posts.filter(p => p.username == username);
  }

  public getStoriesByUser(username : String) : Story[] {
    return this.stories.filter(s => s.username == username);
  }
}
