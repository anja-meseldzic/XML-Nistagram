import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import { AuthService } from '../../auth-service/auth.service';
import { AgentDTO } from '../../DTOs/agent-dto';

@Component({
  selector: 'app-register-agent-admin',
  templateUrl: './register-agent-admin.component.html',
  styleUrls: ['./register-agent-admin.component.css']
})
export class RegisterAgentAdminComponent implements OnInit {

  name = '';
  lastName = '';
  email = '';
  phoneNumber = '';
  website = '';
  biography = '';
  birthDate = '';
  gender = 'Select gender';
  username = '';
  password = '';


  constructor(private _snackBar: MatSnackBar, private router: Router, private authService : AuthService) { }

  ngOnInit(): void {
   
  }

  register(): void {
    if (!this.validateRequiredFields()) {
      this.openSnackBar('First name, Last name, Website, Gender, BirthDate, Email, Username and Password are required', 'Okay');
      return;
    }
    if (!this.validateRegExp('^[0-9]*$', this.phoneNumber)) {
      this.openSnackBar('Phone number can take only digits', 'Okay');
      return;
    }
    if (!this.validateRegExp('^([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})?$', this.email)) {
      this.openSnackBar('Email is not valid', 'Okay');
      return;
    }
    if (!this.validateRegExp('^(((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*)?$', this.website)) {
      this.openSnackBar('Website url is not valid', 'Okay');
      return;
    }

    this.authService.registerAgentByAdmin(new AgentDTO(this.name, this.lastName, this.gender, this.email, this.phoneNumber,
      this.birthDate + 'T00:00:00.123Z', this.website, this.biography, this.username, this.password)).subscribe(
     (data) => {      
       let message = data;
      this.openSnackBar(message, "Okay");
  },
  error => {
    this.openSnackBar(error.error, "Okay");
  } );
   
  }

  
  cancel(): void {
    this.router.navigate(['../agent-requests']);
  }

  validateRequiredFields(): boolean {
    return this.name.trim() !== '' && this.lastName !== '' && this.website.trim() !== '' && this.gender.trim() !== 'Select gender'
   && this.birthDate.trim() !== '' && this.email.trim() !== '' && this.username.trim() !== '' && this.password.trim() !== '';
  }

  validateRegExp(reg: string, toTest: string): boolean {
    const regExp = new RegExp(reg);
    return regExp.test(toTest);
  }

  openSnackBar(message: string, action: string): void {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }

}
