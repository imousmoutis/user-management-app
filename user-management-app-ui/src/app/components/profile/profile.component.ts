import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  userForm: FormGroup;


  constructor(public formBuilder: FormBuilder, private userService: UserService) {
  }

  ngOnInit(): void {
    this.reactiveForm()
    this.getUserProfile()
  }

  reactiveForm() {
    this.userForm = this.formBuilder.group({
      firstName: [''],
      lastName: [''],
      username: ['']
    })
  }

  getUserProfile() {
    this.userService.getUser().subscribe(
      (response) => {
        this.userForm.controls.firstName.setValue(response.firstName);
        this.userForm.controls.lastName.setValue(response.lastName);
        this.userForm.controls.username.setValue(response.username);
      })
  }

}
