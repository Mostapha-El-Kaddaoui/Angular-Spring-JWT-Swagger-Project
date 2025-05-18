import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {jwtDecode} from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated: boolean = false;
  roles: any;
  username: any;
  accessToken!: any;

  constructor(private http:HttpClient, private router: Router) { }

  public login(username: string, password: string){
    let params= new HttpParams().set("username", username).set("password", password);
    let options = {
      headers : new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    return this.http.post("http://localhost:8085/auth/login", params, options)
  }

  loadProfile(data: any){

    this.isAuthenticated = true;
    this.accessToken = data['access_token'];
    let decodedJwt:any = jwtDecode(this.accessToken);
    this.username = decodedJwt.sub;
    this.roles = decodedJwt.scope;
    window.localStorage.setItem("jwt-token",this.accessToken);

  }

  logout() {
    this.isAuthenticated=false;
    this.accessToken=undefined;
    this.roles=undefined;
    this.username=undefined;
    this.router.navigateByUrl("/login");
  }

  loadJwtFromLocalStorage() {
    let token = window.localStorage.getItem("jwt-token");

    if(token){
      this.loadProfile({"access_token":token});
      this.router.navigateByUrl("/admin/customers");
    }
  }
}

