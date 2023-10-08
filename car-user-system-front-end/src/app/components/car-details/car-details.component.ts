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
  
  message = '';

  constructor(
    private carService: CarService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {    
    if (!this.viewMode) {
      this.message = '';
      this.getCar(this.route.snapshot.params["id"]);
    }
  }

  getCar(id: string): void {
    this.carService.get(id)
      .subscribe({
        next: (data) => {
          this.currentCar = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }  

  updateCar(): void {
    this.message = '';

    this.carService.update(this.currentCar.id, this.currentCar)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This car was updated successfully!';
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

}