import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {LoginUser} from '../../dto/login-user.dto';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthenticateService} from '../../services/authenticate.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(public formBuilder: FormBuilder, private userService: UserService,
              private authenticateService: AuthenticateService, private matSnackBar: MatSnackBar, private router: Router) {
  }

  ngOnInit(): void {
    this.reactiveForm()
    if (this.authenticateService.isLoggedIn()) {
      this.router.navigate(['profile']).then();
    }
  }

  reactiveForm() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  submitForm() {
    this.userService.authenticate(
      new LoginUser(this.loginForm.get('username')?.value, this.loginForm.get('password')?.value)).subscribe(
      (response) => {
        this.authenticateService.saveToken(response.token);
        this.matSnackBar.open("You are being redirected to the login page.");
      },
      (error) => {
        this.matSnackBar.open("The credentials you provided were incorrect.", 'Close');
      })
  }

}
