import {Component, OnInit} from '@angular/core';
import {AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;

  constructor(public formBuilder: FormBuilder, private userService: UserService) {
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
    console.log("hey");
  }

}
