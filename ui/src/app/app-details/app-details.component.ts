import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService, ApplicationService} from "../services";
import {Application} from "../models";

@Component({
  selector: 'app-apps-details',
  templateUrl: './app-details.component.html',
  styleUrls: ['./app-details.component.css']
})
export class AppDetailsComponent implements OnInit, OnDestroy {
  app: Application;
  sub: any;
  contentTypes: string[] = ['VIDEO', 'IMAGE', 'HTML'];

  constructor(private appService: ApplicationService,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private router: Router) {
  }
  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = Number.parseInt(params['id']);
      console.log('getting app with id: ', id);
      this.appService
        .getById(id)
        .subscribe(p => this.app = p);
    });
  }

  ngOnDestroy(){
    this.sub.unsubscribe();
  }

  saveAppDetails(){
    this.appService
      .update(this.app)
      .subscribe(
        data => {
          this.alertService.success('Update successful', true);
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
