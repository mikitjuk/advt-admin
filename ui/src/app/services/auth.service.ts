import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import {AppComponent} from "../app.component";

@Injectable()
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, email: string) {
    return this.http.post(
          AppComponent.API_URL + '/authenticate',
        { name: username, email: email },
        {observe: 'response'})
      .map((response: HttpResponse<any> ) => {
        // console.log(response);
        // console.log(response.headers.get('Authorization'));
        // console.log(response.headers.get('authorization'));
        // console.log(response.body);
        // login successful if there's a jwt token in the response
        // if (user && user.token) {
         if (response.body) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(response.body));
          localStorage.setItem('jwt-token', response.headers.get('Authorization'));
        }

        return response.body.user;
      });
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('jwt-token');
    localStorage.removeItem('currentUser');
  }
}
