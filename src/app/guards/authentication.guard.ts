import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";


@Injectable({
  providedIn: 'root' // This is the recommended way for guards
})
export class AuthenticationGuard implements CanActivate{
  constructor(private authService:AuthService, private router:Router){

  }
  
  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot):Observable<boolean|UrlTree> | Promise<boolean|UrlTree> | boolean | UrlTree{
    if(this.authService.isAuthenticated == true){
      return true;
    }else{
      this.router.navigateByUrl("/login");
      return false;
    }
  }
}