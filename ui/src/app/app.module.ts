import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

// used to create fake backend
// import { fakeBackendProvider } from './_helpers';

import { AppComponent } from './app.component';

import { AlertComponent } from './alert';
import { AuthGuard } from './guard';
import { JwtInterceptor } from './jwt';
import {AlertService, ApplicationService, AuthService, UserService} from './services';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './apps';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { UsersComponent } from './users/users.component';
import { HeaderComponent } from './header/header.component';
import {HomeLayoutComponent} from "./layouts/home-layout.component";
import {LoginLayoutComponent} from "./layouts/login-layout.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    AlertComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UsersComponent,
    HomeLayoutComponent,
    LoginLayoutComponent,
    HeaderComponent
  ],
  providers: [
    AuthGuard,
    AlertService,
    AuthService,
    ApplicationService,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },

    // provider used to create fake backend
    // fakeBackendProvider
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
