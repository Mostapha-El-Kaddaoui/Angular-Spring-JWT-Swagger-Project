import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountDetails } from '../model/account.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  apiUrl:string="http://localhost:8085";  
  constructor(private http:HttpClient) { }

  getAccounts(): Observable<Array<AccountDetails>>{
    return this.http.get<Array<AccountDetails>>(this.apiUrl+"/accounts");
  }
  searchAccounts(accid: string, page: number, size:number): Observable<AccountDetails> {
    return this.http.get<AccountDetails>(this.apiUrl+"/accounts/"+accid+"/pageoperations?page="+page+"&size="+size);
  }
}
