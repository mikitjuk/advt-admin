import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../models';
import { AppComponent } from "../app.component";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }

  getAll() {
    console.log(this.http.get<User[]>(AppComponent.API_URL + '/api/users'));
    return this.http.get<User[]>(AppComponent.API_URL_USERS);
  }

  getById(id: number) {
    return this.http.get(AppComponent.API_URL_USERS + id, );
  }

  create(user: User) {
    return this.http.post(AppComponent.API_URL_USERS, user);
  }

  update(user: User) {
    return this.http.put(AppComponent.API_URL_USERS + user.id, user);
  }

  delete(id: number) {
    return this.http.delete(AppComponent.API_URL_USERS + id);
  }
}
