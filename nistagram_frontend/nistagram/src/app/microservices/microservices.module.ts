import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MicroservicesRoutingModule } from './microservices-routing.module';
import { LandingPageComponent } from './landing-page/landing-page.component';
import {MatSelectModule} from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatRadioModule } from '@angular/material/radio';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatTableModule} from '@angular/material/table';
import { RegistrationComponent } from './auth-service/registration/registration.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { PersonalInfoEditComponent } from './auth-service/personal-info-edit/personal-info-edit.component';
import { LoginComponent } from './auth-service/login/login.component';

@NgModule({
  declarations: [LandingPageComponent, RegistrationComponent, CreatePostComponent, PersonalInfoEditComponent, LoginComponent],
  imports: [
    CommonModule,
    MicroservicesRoutingModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    FormsModule,
    MatSelectModule
  ]
})
export class MicroservicesModule { }
