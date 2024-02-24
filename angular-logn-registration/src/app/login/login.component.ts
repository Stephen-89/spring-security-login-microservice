import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  form: any = {
    username: null,
    password: null
  };
  
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role:any;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().roles[0];
    }
  }

  onSubmit(): void {
	  
    const { username, password } = this.form;

    this.authService.login(username, password).subscribe({
    	
      next: data => {
    	  
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.tokenStorage.getUser().roles[0];
        this.reloadPage();
        
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
        if(this.errorMessage == 'Multi-Factor Authentication Required'){
        	this.tokenStorage.saveUsername(username);
        	this.router.navigate(['/multi-factor']);
        }
      }
      
    });
    
  }

  reloadPage(): void {
    window.location.reload();
  }
}
