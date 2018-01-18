import { Component, OnInit } from '@angular/core';

import { User, Application } from '../models';
import { UserService, ApplicationService } from '../services';

@Component({
  moduleId: module.id,
  templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
  currentUser: User;
  users: User[] = [];
  apps: Application[] = [];

  constructor(private userService: UserService, private applicationService: ApplicationService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    this.loadUsers();
    this.loadApps();
  }

  private loadUsers() {
    this.userService.getAll().subscribe(users => { this.users = users; });
  }

  deleteUser(id: number) {
    this.userService.delete(id).subscribe(() => { this.loadUsers() });
  }

  private loadApps() {
    this.applicationService.getAll().subscribe(apps => { this.apps = apps;});
  }

  deleteApp(id: number) {
    this.applicationService.delete(id).subscribe(() => { this.loadApps() });
  }

}
