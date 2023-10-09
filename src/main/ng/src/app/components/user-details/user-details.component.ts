import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentUser: User = {
    firstName: '',
    lastName: '',
    email: '',
    birthday: '',
    login: '',
    password: '',
    phone: '',
  };
  maxDate = new Date();
  isUpdateFailed = false;
  isUpdateSucess = false;
  
  errorMessage = '';

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {    
    if (!this.viewMode) {
      this.errorMessage = '';
      this.getUser(this.route.snapshot.params["id"]);
    }
  }

  getUser(id: string): void {
    this.userService.get(id)
      .subscribe({
        next: (data) => {
          this.currentUser = data;
        },
        error: (e) => {
          console.log(e);
          
        }
      });
  }  

  updateUser(): void {
    this.errorMessage = '';

    this.userService.update(this.currentUser.id, this.currentUser)
      .subscribe({
        next: (res) => {
          this.isUpdateFailed = false;
          this.isUpdateSucess = true;
          this.reloadPage();
        },
        error: (e) => {
          this.isUpdateFailed = true;
          this.isUpdateSucess = false;
          this.errorMessage = e.error.message;
        }
      });
  }

  deleteUser(): void {
    this.userService.delete(this.currentUser.id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['/users']);
        },
        error: (e) => console.error(e)
      });
  }

  reloadPage(): void {
    setTimeout(function(){
      window.location.reload();
   }, 1000);
  }

}