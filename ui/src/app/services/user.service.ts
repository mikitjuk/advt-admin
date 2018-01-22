import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';

import { User } from '../models';
import { AppComponent } from "../app.component";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<User[]>{
    return this.http.get<User[]>(AppComponent.API_URL_USERS);
  }

    getById(id: number): Observable<User> {
    return this.http.get<User>(`${AppComponent.API_URL}/users/${id}`)
  }

  create(user: User) {
    return this.http.post(AppComponent.API_URL_USERS, user);
  }

  update(user: User) {
    //JSON.stringify(person),
    return this.http.put(`${AppComponent.API_URL}/users/${user.id}`, user);
  }

  delete(id: number) {
    return this.http.delete(`${AppComponent.API_URL}/users/${id}`);
  }
}

function mapUsers(response: HttpResponse<User[]> ): User[]{
  return response.body.map(toUser)
}

function toUser(r:any): User{
  let user = <User>({
    id: r.id,
    url: r.url,
    name: r.name,
    email: r.email,
    role: r.role,
  });
  console.log('Parsed user:', user);
  return user;
}
function extractId(personData:any){
  let extractedId = personData.url.replace('http://swapi.co/api/people/','').replace('/','');
  return parseInt(extractedId);
}

function mapPerson(response:HttpResponse<any>): User{
  return toUser(response.body());
}

// this could also be a private method of the component class
function handleError (error: any) {
  let errorMsg = error.message || `Some Error!`;
  console.error(errorMsg);
  return Observable.throw(errorMsg);
}
