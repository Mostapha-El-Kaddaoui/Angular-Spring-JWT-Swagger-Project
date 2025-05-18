import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { catchError, Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor{

  constructor(private authService:AuthService){}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    //interceptor doit pas envoyer accesstoken dans le login car accesstoken = undifined , after the login we get our accesstoken
    if(!request.url.includes("/auth/login")){
      let newRequest = request.clone({
        headers: request.headers.set('Authorization', 'Bearer '+this.authService.accessToken)
      }); 
      return next.handle(newRequest).pipe(
        catchError(err =>{
          if(err.status == 401){
            this.authService.logout();
          }
          return throwError(err.message);
        })
      ); 
    }else{
      return next.handle(request);
    }
    
  }
  
}