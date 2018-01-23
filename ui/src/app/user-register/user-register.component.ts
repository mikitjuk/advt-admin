import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService, AlertService} from "../services";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../models";
import {AlertComponent} from "../alert";
import {register} from "ts-node/dist";

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent implements OnInit {
  user: any ={};
  roles: string[] = ['ADMIN', 'ADOPS', 'PUBLISHER'];

  constructor(private userService: UserService,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
  }

  register(){
    this.userService
      .create(this.user)
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.gotoUsersList()
        },
        error => {
          this.alertService.error(error);
        });
  }

  gotoUsersList(){
    this.router.navigate(['/users']);
  }
}
