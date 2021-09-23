import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginUser} from '../dto/login-user.dto';
import {JWT} from '../dto/jwt.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  serverEndpoint = "http://localhost:8080/api"

  constructor(private httpClient: HttpClient) {
  }

  httpHeader = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  authenticate(user: LoginUser): Observable<JWT> {
    return this.httpClient.post<JWT>(this.serverEndpoint + '/user/authenticate',
      JSON.stringify(user), this.httpHeader)
  }

  getUserByUsername(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.serverEndpoint + '/user/exists/' + username)
    .pipe()
  }
}
