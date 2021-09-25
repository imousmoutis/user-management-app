import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginUser} from '../dto/login-user.dto';
import {JWT} from '../dto/jwt.dto';
import {User} from '../dto/user.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  serverEndpoint = "http://localhost:8080/api"
  httpHeaders = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) {
  }

  authenticate(user: LoginUser): Observable<JWT> {
    return this.httpClient.post<JWT>(this.serverEndpoint + '/authenticate',
      JSON.stringify(user), this.httpHeaders).pipe()
  }

  register(user: User): Observable<JWT> {
    return this.httpClient.post<JWT>(this.serverEndpoint + '/register', JSON.stringify(user),
      this.httpHeaders).pipe()
  }

  getUserByUsername(username: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.serverEndpoint + '/user/exists/' + username)
    .pipe()
  }

  getUser(): Observable<User> {
    return this.httpClient.get<User>(this.serverEndpoint + '/user', this.httpHeaders).pipe()
  }
}
