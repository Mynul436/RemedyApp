import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) {
   }
  // user add
  public addUser(user:any){
      return this.http.post(`{$baseUrl}/user`,user);
  }
}
