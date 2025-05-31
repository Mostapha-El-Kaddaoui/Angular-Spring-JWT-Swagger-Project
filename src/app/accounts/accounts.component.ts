import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { catchError, Observable, throwError, of } from 'rxjs';
import { AccountDetails } from '../model/account.model';
import { AccountService } from '../services/account.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  searchFormGroup!: FormGroup;
  updateAccountFormGroup!: FormGroup;

  modalOpen: boolean = false;
  accountToUpdate!: any;
  accounts$!: Observable<any[]>; 
  currentPage: number = 0;
  sizePage: number = 5;
  accountObservable$!: Observable<AccountDetails>;
  errorMessage: string | null = null;

  constructor(private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      accountId: this.fb.control('', [Validators.required, Validators.minLength(5)]),
    });
    this.updateAccountFormGroup = this.fb.group({
      accountId: this.fb.control('', [Validators.required, Validators.minLength(5)]),
      type: this.fb.control('', [Validators.required, Validators.minLength(5)]),
      balance: this.fb.control('', [Validators.required, Validators.min(0)]),
      duration: this.fb.control('', [Validators.required, Validators.min(1)]),
      overDraft: this.fb.control('', [Validators.required, Validators.min(0)]),
    });
    this.loadAccounts();
  }

  loadAccounts() {
    this.errorMessage = null;
    this.accounts$ = this.accountService.getAccounts().pipe(
      catchError((err: any) => {
        this.errorMessage = err.message || 'Failed to load accounts';
        return of([]);
      })
    );
  }

  handleSearchAccount() {
    if (this.searchFormGroup.invalid) return;
    
    this.errorMessage = null;
    const accId = this.searchFormGroup.value.accountId;
    this.accountObservable$ = this.accountService.searchAccounts(accId, this.currentPage, this.sizePage).pipe(
      catchError((err: any) => {
        this.errorMessage = err.message || 'Account not found';
        return throwError(() => err);
      })
    );
  }

  handleDeleteAccount(account: any) {
    if (confirm(`Are you sure you want to delete account ${account.id}?`)) {
      // Remove the delete functionality since it's not implemented in the service
      this.errorMessage = 'Delete functionality not implemented';
    }
  }

  handleUpdateAccount() {
  if (this.updateAccountFormGroup.invalid) return;

  const formValue = this.updateAccountFormGroup.value;
  const accountId = formValue.accountId;
  
  // Prepare the payload with explicit type information
  let payload;
    if (formValue.type === 'SAVING') {
      payload = {
        type: 'SAVING', // Explicitly set the type
        balance: formValue.balance,
        duration: formValue.duration,
        customerDTO: this.accountToUpdate.customerDTO
      };
    } else if (formValue.type === 'CURRENT') {
      payload = {
        type: 'CURRENT', // Explicitly set the type
        balance: formValue.balance,
        overDraft: formValue.overDraft,
        customerDTO: this.accountToUpdate.customerDTO
      };
    } else {
      this.errorMessage = 'Invalid account type';
      return;
  }

  console.log('Sending payload:', payload); // Debug log

  this.accountService.updateAccount(accountId, payload).subscribe({
    next: (response) => {
      alert('Account updated successfully');
      this.closeModal();
      this.loadAccounts();
    },
    error: (err) => {
      this.errorMessage = err.error?.message || err.message || 'Failed to update account';
      console.error('Update error:', err);
    }
  });
}
  
  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }

  getPagesArray(totalPages: number): number[] {
    return Array.from({length: totalPages}, (_, i) => i);
  }
  openModal(account: any) {
    this.accountToUpdate = account;
    this.accountToUpdate.customerDTO = account.customerDTO || {}; 

    console.log('Opening modal for account:', this.accountToUpdate);
    this.updateAccountFormGroup.patchValue({
      accountId: account.id,
      type: account.type, 
      balance: account.balance,
      duration: account.duration || '', 
      overDraft: account.overDraft || ''
    });
    
    this.modalOpen = true;
  }
  closeModal() {
    this.modalOpen = false;
  }
}