import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {LoginUser} from '../../dto/login-user.dto';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(public formBuilder: FormBuilder, private userService: UserService, private matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.reactiveForm()
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
        this.matSnackBar.open("You are being redirected to the login page.", '', {
          duration: 3000
        });
      },
      (error) => {
        this.matSnackBar.open("The credentials you provided were incorrect.", 'Close', {
          duration: 3000
        });
      })
  }

}
