import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})

export class AddUserComponent implements OnInit {

  user: User = {
    firstName: '',
    lastName: '',
    email: '',
    birthday: '',
    login: '',
    password: '',
    phone: '',
  };
  submitted = false;
  isCreateFailed = false;
  registerSucess = false;
  maxDate = new Date();

  errorMessage = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  saveUser(): void {
    const data = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      birthday: this.user.birthday,
      login: this.user.login,
      password: this.user.password,
      phone: this.user.phone,
    };

    this.userService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
          this.isCreateFailed = false;
          this.registerSucess = true;
          this.reloadPage();

        },
        error: (e) =>{
          this.errorMessage = e.error.message;
          this.registerSucess = false;
          this.isCreateFailed = true;
        }
      });
  }

  newUser(): void {
    this.submitted = false;
    this.user = {
      firstName: '',
      lastName: '',
      email: '',
      birthday: '',
      login: '',
      password: '',
      phone: '',
    };
  }

  reloadPage(): void {
    setTimeout(function(){
      window.location.reload();
   }, 1000);
  }

}
