import {Component, OnInit} from '@angular/core';
import {AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {UserService} from '../../services/user.service';
import {User} from '../../dto/user.dto';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(public formBuilder: FormBuilder, private userService: UserService, private matSnackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.reactiveForm()
  }

  reactiveForm() {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', [Validators.required], [this.usernameValidator()]],
      password: ['', Validators.required],
      repeatPassword: ['', Validators.required]
    }, {validators: this.checkPasswords})
  }

  get f() {
    return this.registerForm.controls;
  }

  checkPasswords(group: FormGroup) {
    const pass = group.controls.password.value;
    const confirmPass = group.controls.repeatPassword.value;

    return pass === confirmPass ? null : {passwordsDoNotMatch: true};
  }

  //async validator to check if username already exists in the database
  usernameValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.userService.getUserByUsername(control.value).pipe(
        map(res => {
          return res ? {usernameExists: true} : null;
        })
      );
    };
  }

  submitForm() {
    this.userService.register(
      new User(this.registerForm.get('firstName')?.value, this.registerForm.get('lastName')?.value,
        this.registerForm.get('username')?.value, this.registerForm.get('password')?.value)).subscribe(
      (response) => {
        this.matSnackBar.open("You have successfully registered.");
      },
      (error) => {
        console.log(error)
        this.matSnackBar.open("An error occurred. Please try again.", 'Close');
      })
  }

}
