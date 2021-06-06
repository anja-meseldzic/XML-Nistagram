import { Injectable } from '@angular/core';
import {AuthService} from '../auth-service/auth.service';
import {ActivatedRouteSnapshot, Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {
  constructor(public authService: AuthService, public router: Router) { }
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string[] = route.data.expectedRoles;
    if (!expectedRoles.includes(this.authService.getRole()) && this.authService.isLoggedIn()) {
      this.router.navigate(['./feed']);
      return false;
    }
    if (!this.authService.isLoggedIn() && expectedRoles.length > 0) {
      this.router.navigate(['./']);
      return false;
    }
    return true;
  }
}
