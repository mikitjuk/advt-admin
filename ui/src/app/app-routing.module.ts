import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';

import {AppsComponent} from './apps';
import {LoginComponent} from './login';
import {UsersComponent} from "./users/users.component";
import {AuthGuard} from "./guard";
import {HomeLayoutComponent} from "./layouts/home-layout.component";
import {LoginLayoutComponent} from "./layouts/login-layout.component";
import {HomeComponent} from "./home/home.component";
import {UserDetailsComponent} from "./user-details/user-details.component";
import {AppsDetailsComponent} from "./apps-details/apps-details.component";

const appRoutes: Routes = [
  {
    path: '',
    component: HomeLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'home',component: HomeComponent,},
      { path: 'apps', component: AppsComponent,},
      { path: 'apps/:id', component: AppsDetailsComponent },
      { path: 'users', component: UsersComponent },
      { path: 'users/:id', component: UserDetailsComponent },
    ]
  },
  {
    path: '',
    component: LoginLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent },
    ]
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
