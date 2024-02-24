import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const API_URL = 'http://localhost:8080/spring-auth/';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private http: HttpClient) { }

  generateQrImage(): Observable<any> {
    let headers = new HttpHeaders();
    headers.append('Access-Control-Allow-Origin', '*');
    return this.http.get(API_URL + 'generate-qr-image', { headers:headers });
  }

  disableMfa(): Observable<any> {
    let headers = new HttpHeaders();
    headers.append('Access-Control-Allow-Origin', '*');
    return this.http.post(API_URL + 'disable-mfa', null, { headers:headers });
  }
  
}
