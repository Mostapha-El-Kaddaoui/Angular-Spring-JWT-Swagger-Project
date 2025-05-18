import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, FormControlName } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  formLogin!: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router){}

  ngOnInit(): void {
    this.formLogin = this.fb.group({
      username: this.fb.control(""),
      password: this.fb.control("")
    })
  }
  handleLogin(){
    let username = this.formLogin.value.username;
    let password = this.formLogin.value.password;
    this.authService.login(username,password).subscribe({
      
      next: data => {
        this.authService.loadProfile(data);
        this.router.navigateByUrl("/admin")
      },
      error: err =>{
        console.log("error : "+ err)
      }
    })
  }
  changePassword(){
    this.router.navigateByUrl("/changepassword")
  }
}
