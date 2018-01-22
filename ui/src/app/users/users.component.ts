import {Component, OnInit} from '@angular/core';
import {UserService} from "../services";
import {User} from "../models";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.loadUsers();
  }

  private loadUsers() {
    this.userService.getAll().subscribe(users => this.users = users);
  }

  deleteUser(id: number) {
    this.userService.delete(id).subscribe(() => { this.loadUsers() });
  }
}
