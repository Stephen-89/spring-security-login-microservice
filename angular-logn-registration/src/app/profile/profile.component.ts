import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { AuthService } from '../_services/auth.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  
  currentUser: any;
  checked = false;
  qrImage = '';
  
  constructor(private tokenStorageService: TokenStorageService, private authService: AuthService, private userService: UserService) { }

  ngOnInit(): void {
	this.authService.authenticate();  
    this.currentUser = this.tokenStorageService.getUser();
    this.checked = this.currentUser.mfaEnabled;
    if(this.checked){
    	this.generateQrImage();
    }
  }
  
  changeEvent(result:boolean){
	  if(result){
		  this.currentUser.isMfaEnabled = true;
		  this.generateQrImage();
	  } else {
		  this.currentUser.isMfaEnabled = false;
		  this.disableMfa();
	  }
	  this.tokenStorageService.saveUser(this.currentUser);
  }
  
  generateQrImage(){
	this.userService.generateQrImage().subscribe(result => {
	  this.qrImage = result.image;
	});
  }

  disableMfa(){
	this.userService.disableMfa().subscribe(result => {
	});
  }
  
}
