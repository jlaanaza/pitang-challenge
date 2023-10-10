import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/model/user.model';
import * as moment from 'moment';


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
          data.birthday = moment(data.birthday).add(1, 'day').format('yyyy-MM-DD');
          this.currentUser = data;
        },
        error: (e) => {
          console.log(e);
          
        }
      });
  }  

  updateUser(): void {
    this.errorMessage = '';
    this.currentUser.birthday = moment(this.currentUser.birthday).subtract(1, 'day').format('yyyy-MM-DD');

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