import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import {MatSelectModule} from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatFormFieldModule } from '@angular/material/form-field'; 
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatTableModule} from '@angular/material/table';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgImageSliderModule } from 'ng-image-slider';
import { MatAutocompleteModule } from '@angular/material/autocomplete'; 
import { MatListModule } from '@angular/material/list'; 
import { AuthInterceptorService } from './interceptors/auth-interceptor.service';
import {MatDialogModule} from '@angular/material/dialog';
import { FollowerRequestDialogComponent } from './microservices/components/follower-request-dialog/follower-request-dialog.component';
import { FollowersDialogComponent } from './microservices/components/followers-dialog/followers-dialog.component';
import { CollectionDialogComponent } from './microservices/components/collection-dialog/collection-dialog.component';
import { CloseFriendsComponent } from './microservices/components/close-friends/close-friends.component';



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    FormsModule,
    MatSelectModule,
    HttpClientModule,
    NgbModule,
    NgImageSliderModule,
    MatAutocompleteModule,
    MatListModule,
    MatDialogModule
    
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptorService,
    multi: true
  }],
  entryComponents:[FollowerRequestDialogComponent, FollowersDialogComponent, CollectionDialogComponent, CloseFriendsComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
