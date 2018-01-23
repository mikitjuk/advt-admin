import { Component, OnInit } from '@angular/core';
import {AlertService, ApplicationService} from "../services";
import {ActivatedRoute, Router} from "@angular/router";
import {Application} from "../models";

@Component({
  selector: 'app-app-register',
  templateUrl: './app-register.component.html',
  styleUrls: ['./app-register.component.css']
})
export class AppRegisterComponent implements OnInit {
  app: Application;
  contentTypes: string[] = ['VIDEO', 'IMAGE', 'HTML'];

  constructor(private appService: ApplicationService,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
  }

  register(){
    this.appService
      .create(this.app)
      .subscribe(
        data => {
          this.alertService.success('Create successful', true);
          this.backStart();
        },
        error => {
          this.alertService.error(error);
        });
  }

  private backStart() {
    this.router.navigate(['/apps']);
  }

  gotoAppsList(){
    this.backStart();
  }
}
