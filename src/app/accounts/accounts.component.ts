import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountDetails } from '../model/account.model';
import { AccountService } from '../services/account.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './accounts.component.html',
  styleUrl: './accounts.component.css'
})
export class AccountsComponent implements OnInit {
  searchFormGroup!: FormGroup;
  accounts$!: Observable<Array<AccountDetails>>;
  currentPage: number=0;
  sizePage: number=5;
  accountObservable$!: Observable<AccountDetails>;
  constructor(private accountService: AccountService, private fb : FormBuilder){};

  ngOnInit(): void {
    this.searchFormGroup=this.fb.group({
      accountId : this.fb.control('',[Validators.required, Validators.minLength(5)]),
    });
    this.accounts$ = this.accountService.getAccounts().pipe(
      catchError(err=>{
        return throwError(err);
      }),
    )
  }
  handleSearchAccount() {
    console.log("seachd")
    const accId = this.searchFormGroup.value.accountId;
    this.accountObservable$ = this.accountService.searchAccounts(accId,this.currentPage,this.sizePage).pipe(
      catchError(err => throwError(err))
    );
  }

  gotoPage(page: number){
    this.currentPage=page;
    this.handleSearchAccount();
  }
}
