import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/model/car.model';
import { CarService } from 'src/app/services/car.service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent implements OnInit {

  cars?: Car[];
  currentCar: Car = {};
  currentIndex = -1;
  email = '';

  constructor(private carService: CarService) { }

  ngOnInit(): void {
    this.retrieveCars();
  }

  retrieveCars(): void {
    this.carService.getAll()
      .subscribe({
        next: (data) => {
          this.cars = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  refreshList(): void {
    this.retrieveCars();
    this.currentCar = {};
    this.currentIndex = -1;
  }

  setActiveCar(car: Car, index: number): void {
    this.currentCar = car;
    this.currentIndex = index;
  } 

}