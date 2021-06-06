import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { MediaService } from '../media-service/media.service';

@Component({
  selector: 'app-reactions-list',
  templateUrl: './reactions-list.component.html',
  styleUrls: ['./reactions-list.component.css']
})
export class ReactionsListComponent implements OnInit {

  public likes : string[] = [];
  public dislikes : string[] = [];

  constructor(private mediaService : MediaService, private route : ActivatedRoute, private router : Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.mediaService.getAllReactions(Number(this.route.snapshot.paramMap.get('id'))).subscribe(
      (data) => {
        this.likes = data.likes;
        this.dislikes = data.dislikes;
      }
    );
  }

}
