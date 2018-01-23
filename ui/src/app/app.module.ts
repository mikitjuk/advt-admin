import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AlertComponent } from './alert';
import { AuthGuard } from './guard';
import { JwtInterceptor } from './jwt';
import { AlertService, ApplicationService, AuthService, UserService} from './services';
import { AppRoutingModule } from './app-routing.module';
import { AppsComponent } from './apps';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { UsersComponent } from './users/users.component';
import { HeaderComponent } from './header/header.component';
import { HomeLayoutComponent } from "./layouts/home-layout.component";
import { LoginLayoutComponent } from "./layouts/login-layout.component";
import { HomeComponent } from './home/home.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { AppDetailsComponent } from './app-details/app-details.component';
import { UserRegisterComponent } from './user-register/user-register.component';
import { AppRegisterComponent } from './app-register/app-register.component';

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
    AppsComponent,
    LoginComponent,
    RegisterComponent,
    UsersComponent,
    HomeLayoutComponent,
    LoginLayoutComponent,
    HeaderComponent,
    HomeComponent,
    UserDetailsComponent,
    AppDetailsComponent,
    UserRegisterComponent,
    AppRegisterComponent
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
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
