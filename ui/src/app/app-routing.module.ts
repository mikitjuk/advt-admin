import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { HomeComponent } from './apps';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import {UsersComponent} from "./users/users.component";
import {AuthGuard} from "./guard";
import {HomeLayoutComponent} from "./layouts/home-layout.component";
import {LoginLayoutComponent} from "./layouts/login-layout.component";

const appRoutes: Routes = [
  {
    path: '',
    component: HomeLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'apps',component: HomeComponent,},
      { path: 'users', component: UsersComponent }
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
    ]
  },

  { path: 'users', component: UsersComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
