import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import { NewCustomerComponent } from './new-customer/new-customer.component';
import { LoginComponent } from './login/login.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { AuthenticationGuard } from './guards/authentication.guard';
import { AuthorizationGuard } from './guards/authorization.guard';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

export const routes: Routes = [
    {path : "home", component: HomeComponent},
    {path : "login", component: LoginComponent},
    {path : "", redirectTo : "/login", pathMatch: "full"},
    {path : "admin", component: AdminTemplateComponent, canActivate:[AuthenticationGuard],
    children:[
        {path : "customers", component: CustomersComponent},
        {path : "accounts", component: AccountsComponent},
        {path : "customers/new-customer", component: NewCustomerComponent, canActivate:[AuthorizationGuard], data:{role:'ADMIN'}},
        {path : "notauthorized", component: NotAuthorizedComponent},
    ]},
    {path : "changepassword", component: ChangePasswordComponent}



];
