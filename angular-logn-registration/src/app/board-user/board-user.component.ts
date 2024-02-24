import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.css']
})
export class BoardUserComponent implements OnInit {
  
  currentUser: any;
  content?: string;

  constructor(private userService: UserService, private authService: AuthService) { }

  ngOnInit(): void {
	this.authService.authenticate();
	this.content = 'User Board';
  }
}
