import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  currentUser: any;
  content?: string;

  constructor(private userService: UserService, private authService: AuthService, private tokenStorageService: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
	this.authService.authenticate();
	this.currentUser = this.tokenStorageService.getUser();
	this.currentUser.roles.forEach(role => {
		if(role.name != 'ROLE_ADMIN'){
			this.router.navigate(['/profile']);
		}
	});
    this.content = 'Admin Board';
  }
  
}