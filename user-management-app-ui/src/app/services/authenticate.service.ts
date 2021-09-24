import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';
import {AuthMessageService} from './auth-message.service';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const USER_ROLES = 'auth-user-roles';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor(private jwtHelperService: JwtHelperService, private router: Router, private authMessageService: AuthMessageService) {
  }

  public saveToken(token: string): void {
    window.sessionStorage.clear();
    window.sessionStorage.setItem(TOKEN_KEY, token);

    let decodedHeader = this.jwtHelperService.decodeToken(token);
    window.sessionStorage.setItem(USER_KEY, decodedHeader.sub);
    window.sessionStorage.setItem(USER_ROLES, decodedHeader.roles);

    if (decodedHeader.roles.includes("ADMIN")) {
      this.router.navigate(['admin']).then();
    } else {
      this.router.navigate(['profile']).then();
    }

    this.authMessageService.updateMessage(true);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public isLoggedIn(): boolean {
    let authToken = window.sessionStorage.getItem(TOKEN_KEY);
    return (authToken !== null);
  }

  public getUser(): string {
    return <string>window.sessionStorage.getItem(USER_KEY);
  }

  public getRole(): string {
    return <string>window.sessionStorage.getItem(USER_ROLES);
  }

  public logout(): void {
    window.sessionStorage.clear();
    this.router.navigate(['login']).then();
    this.authMessageService.updateMessage(false);
  }
}
