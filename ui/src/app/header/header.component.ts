import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services";
import {User} from "../models";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  currentUser: User;

  constructor(private authService: AuthService) {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  onLogout(){
    this.authService.logout();
  }

  ngOnInit() {
  }

}
