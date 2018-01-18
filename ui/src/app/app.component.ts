import { Component } from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'app',
  templateUrl: 'app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  static API_URL="http://localhost:8080";
  static API_URL_USERS="http://localhost:8080/api/users";
  static API_URL_APPS="http://localhost:8080/api/apps";
}
