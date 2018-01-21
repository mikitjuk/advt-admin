import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { AlertService, AuthService } from '../services';

@Component({
  moduleId: module.id,
  templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  returnUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthService,
    private alertService: AlertService) { }

  ngOnInit() {
    // reset login status
    this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'];
    if(!this.returnUrl || this.returnUrl === '/') {
      this.returnUrl = '/apps';
    }

    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/apps';
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.model.name, this.model.email)
      .subscribe(
        data => {
          console.log(this.returnUrl);
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
