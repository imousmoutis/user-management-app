import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';

const TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root'
})
export class AuthMessageService {

  private isLoggedIn = new Subject<boolean>();

  constructor() {
  }

  public getMessage(): Observable<boolean> {
    return this.isLoggedIn.asObservable();
  }

  public updateMessage(message: boolean): void {
    this.isLoggedIn.next(message);
  }
}
