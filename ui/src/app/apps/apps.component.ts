import { Component, OnInit } from '@angular/core';

import { User, Application } from '../models';
import { UserService, ApplicationService } from '../services';

@Component({
  moduleId: module.id,
  templateUrl: 'apps.component.html'
})

export class AppsComponent implements OnInit {
  currentUser: User;
  apps: Application[] = [];

  constructor(private applicationService: ApplicationService) {
  }

  ngOnInit() {
    this.loadApps();
  }

  private loadApps() {
    this.applicationService.getAll().subscribe(apps => { this.apps = apps;});
  }

  deleteApp(id: number) {
    this.applicationService.delete(id).subscribe(() => { this.loadApps() });
  }
}
