import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ApplicationService} from "../services";
import {Application} from "../models";

@Component({
  selector: 'app-apps-details',
  templateUrl: './apps-details.component.html',
  styleUrls: ['./apps-details.component.css']
})
export class AppsDetailsComponent implements OnInit, OnDestroy {
  app: Application;
  sub: any;


  constructor(private appService: ApplicationService,
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

  saveAppsDetails(){
    this.appService
      .update(this.app)
      .subscribe(r => console.log(`saved!!! ${JSON.stringify(this.app)}`));
    this.backStart();
  }

  private backStart() {
    this.router.navigate(['/apps']);
  }

  gotoUsersList(){
    this.backStart();
  }

}
