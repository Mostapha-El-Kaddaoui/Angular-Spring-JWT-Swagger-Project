import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  imports: [],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent implements OnInit {
  constructor(private router:Router){
    
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  changePassword(){

  }

}
