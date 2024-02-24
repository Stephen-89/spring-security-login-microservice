import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../_services/auth.service';
import { TokenStorageService } from '../../_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './multi-factor.component.html',
  styleUrls: ['./multi-factor.component.css']
})
export class MultiFactorComponent implements OnInit {
  
  form: any = {
    mfaCode: null
  };
  
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit(): void {
	  
    const { mfaCode } = this.form;
    let username = this.tokenStorage.getUsername();
    
    this.authService.verifyMfa(username, mfaCode).subscribe({
    	
      next: data => {
    	  
    	if(data == false){
    		this.isLoginFailed = true;
    		this.errorMessage = 'Incorrect Multi-Factor Code'
    	} else {
            this.tokenStorage.saveToken(data.accessToken);
            this.tokenStorage.saveUser(data);
            this.isLoginFailed = false;
            this.isLoggedIn = true;
            this.reloadPage();
    	}
    	 
      },
      error: err => {
        this.errorMessage = err.error.message;
      }
      
    });
    
  }

  reloadPage(): void {
    window.location.reload();
  }
}
