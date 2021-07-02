import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, Router} from "@angular/router";
import {AuthServiceService} from "../auth-service/auth-service.service";

@Injectable({
  providedIn: 'root'
})
export class GuardService {
  constructor(public authService: AuthServiceService, public router: Router) { }
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string[] = route.data.expectedRoles;
    if (!expectedRoles.includes(this.authService.getRole()) && this.authService.isLoggedIn()) {
      this.router.navigate(['./products']);
      return false;
    }
    if (!this.authService.isLoggedIn() && expectedRoles.length > 0) {
      this.router.navigate(['./']);
      return false;
    }
    return true;
  }
}
