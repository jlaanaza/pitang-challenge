import { Component, Input, OnInit } from '@angular/core';
import { CarService } from 'src/app/services/car.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Car } from 'src/app/model/car.model';

@Component({
  selector: 'app-car-details',
  templateUrl: './car-details.component.html',
  styleUrls: ['./car-details.component.css']
})
export class CarDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentCar: Car = {
    year: undefined,
    licensePlate: '',
    model: '',
    color: '',
  };
  isUpdateFailed = false;
  isUpdateSucess = false;
  errorMessage = '';

  constructor(
    private carService: CarService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {    
    if (!this.viewMode) {
      this.errorMessage = '';
      this.getCar(this.route.snapshot.params["id"]);
    }
  }

  getCar(id: string): void {
    this.carService.get(id)
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

  updateCar(): void {
    this.errorMessage = '';

    this.carService.update(this.currentCar.id, this.currentCar)
      .subscribe({
        next: (res) => {
          console.log(res);
        },
        error: (e) => console.error(e)
      });
  }

  deleteCar(): void {
    this.carService.delete(this.currentCar.id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['/cars']);
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