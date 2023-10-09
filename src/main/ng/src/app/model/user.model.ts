import { Car } from "./car.model";

export class User {
  id?: any;
  firstName?: string;
  lastName?: string;
  email?: string;
  birthday? : string;
  login?: string;
  password?: string;
  phone?: string;
  cars?: Car[];
  lastLogin?: string;
  createdAt?: string;
}
