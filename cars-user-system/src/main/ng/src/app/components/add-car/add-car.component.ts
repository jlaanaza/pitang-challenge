import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/model/car.model';
import { CarService } from 'src/app/services/car.service';

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})

export class AddCarComponent implements OnInit {

  car: Car = {
    year: undefined,
    licensePlate: '',
    model: '',
    color: '',
  };
  submitted = false;

  constructor(private carService: CarService) { }

  ngOnInit(): void {
  }

  saveCar(): void {
    const data = {
      year: this.car.year,
      licensePlate: this.car.licensePlate,
      model: this.car.model,
      color: this.car.color,
    };

    this.carService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
  }

  newCar(): void {
    this.submitted = false;
    this.car = {
      year: undefined,
      licensePlate: '',
      model: '',
      color: '',
    };
  }

}
