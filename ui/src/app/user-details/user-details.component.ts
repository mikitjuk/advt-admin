import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../services";
import {User} from "../models";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit, OnDestroy {
  user: User;
  sub: any;
  roles: string[] = ['ADMIN', 'ADOPS', 'PUBLISHER'];

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      let id = Number.parseInt(params['id']);
      console.log('getting user with id: ', id);
      this.userService
        .getById(id)
        .subscribe(p => this.user = p);
    });
  }

  ngOnDestroy(){
    this.sub.unsubscribe();
  }

  saveUserDetails(){
    console.log(this.user.id);
      this.userService
        .update(this.user)
        .subscribe(r => console.log(`updated!!! ${JSON.stringify(this.user)}`));
    this.router.navigate(['/users']);
  }

  gotoUsersList(){
    let link = ['/users'];
    this.router.navigate(link);
  }

}
