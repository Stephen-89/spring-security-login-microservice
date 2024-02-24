import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';

const AUTH_API = 'http://localhost:8080/spring-auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type':'application/json', 'Access-Control-Allow-Origin':'*' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
	
  constructor(private http: HttpClient, private router: Router, private tokenStorageService: TokenStorageService) { }

  login(username: string, password: string): Observable<any> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post(AUTH_API + 'login', { username, password}, httpOptions);
  }

  register(firstName: string, secondName: string, username: string, password: string): Observable<any> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post(AUTH_API + 'register', { firstName, secondName, username, password }, { headers:headers });
  }

  verifyMfa(username: string, code: string): Observable<any> {
    let headers = new HttpHeaders();
    headers.set('Content-Type', 'application/json');
    headers.set('Access-Control-Allow-Origin', '*');
    return this.http.post(AUTH_API + 'verfiy-code', { username, code }, { headers:headers });
  }

  authenticate() {
    if (!this.tokenStorageService.getToken()) {
	  this.router.navigate(['/login']);  
    }
  }
  
}
