import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../services/customer.service';
import {catchError, finalize, map, Observable, pipe, throwError} from 'rxjs';
import { Customer } from '../model/customer.model';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-customers',
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css',
  viewProviders: []
})
export class CustomersComponent implements OnInit {
  customers$!: Observable<Array<Customer>>;
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  isLoading: boolean = false;
  deleteLoad: boolean = false;
  constructor(private customerService:CustomerService, private fb : FormBuilder){}
  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword : this.fb.control(""),

    })
    this.isLoading = true;
    this.customers$ = this.customerService.getCustomers().pipe(
      catchError(err=>{
        this.errorMessage = err.message;
        return throwError(err);
      }),
      finalize(() => {
        this.isLoading = false;
      })
    );

  }
  handleSearchCustomers(){
    let kw =  this.searchFormGroup?.value.keyword;
    this.isLoading = true;
    this.customers$ = this.customerService.searchCustomers(kw).pipe(
      catchError(err=>{
        this.errorMessage = err.message;
        return throwError(err);
      }),
      finalize(() => {
        this.isLoading = false;
      })
    );
  }

  handleDeleteCustomer(c:Customer) {
    let confir = confirm("Are you sure");
    if(!confir) return;
    this.customerService.deleteCustomer(c.id).subscribe({
      next:(resp)=>{
        this.customers$=this.customers$.pipe(
          map(data=>{
            let index = data.indexOf(c);
            data.slice(index, 1);
            return data;
          }),
        )
      },
      error: err =>{
        console.log(err);
      }
    })
  }
}
