import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import {Application, User} from '../models';
import { AppComponent } from "../app.component";


@Injectable()
export class ApplicationService {
  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Application[]>(AppComponent.API_URL_APPS);
  }

  getById(id: number) {
    return this.http.get<Application>(`${AppComponent.API_URL}/apps/${id}`)
  }

  create(app: Application) {
    return this.http.post(AppComponent.API_URL_APPS, app);
  }

  update(app: Application) {
    return this.http.put(`${AppComponent.API_URL}/apps/${app.id}`, app);
  }

  delete(id: number) {
    return this.http.delete(`${AppComponent.API_URL}/apps/${id}`);
  }
}
