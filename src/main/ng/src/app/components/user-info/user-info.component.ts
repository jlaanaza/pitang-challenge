import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  @Input() viewMode = false;

  @Input() user: User = {
    firstName: '',
    lastName: '',
    email: '',
    birthday: '',
    login: '',
    password: '',
    phone: '',
    cars: [],
    lastLogin: '',
    createdAt: ''
  };
  
  message = '';

  constructor(
    private authService: AuthService) { }

  ngOnInit(): void {    
    if (!this.viewMode) {
      this.message = '';
      this.getUserInfo();
    }
  }

  getUserInfo(): void {
    this.authService.me()
      .subscribe({
        next: (data) => {
          this.user = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }  

  

}