import {Component, OnInit} from '@angular/core';
import {AuthenticateService} from '../../services/authenticate.service';
import {AuthMessageService} from '../../services/auth-message.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  isLoggedIn: boolean;

  subscription: Subscription;

  constructor(private authMessageService: AuthMessageService, private authenticateService: AuthenticateService) {
  }

  ngOnInit(): void {
    this.subscription = this.authMessageService.getMessage().subscribe(msg => this.isLoggedIn = msg);
    this.authMessageService.updateMessage(this.authenticateService.isLoggedIn());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  logout() {
    this.authenticateService.logout();
  }

}
