import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Customer } from '../model/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  apiUrl:string="http://localhost:8085";
  constructor(private http:HttpClient) { }
  public getCustomers():Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.apiUrl+"/customers");
  }
  public searchCustomers(keyword: string):Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.apiUrl+"/customers/search?keyword="+keyword);
  }
  public saveCustomer(customer: Customer):Observable<Customer>{
    return this.http.post<Customer>(this.apiUrl+"/customers", customer);
  }

  deleteCustomer(id: number) {
    return this.http.delete(this.apiUrl+"/customers/"+id)
  }
}
