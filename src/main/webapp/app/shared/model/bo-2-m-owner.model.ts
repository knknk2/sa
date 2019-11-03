import { IBo2mCar } from 'app/shared/model/bo-2-m-car.model';

export interface IBo2mOwner {
  id?: number;
  name?: string;
  bo2mCars?: IBo2mCar[];
}

export const defaultValue: Readonly<IBo2mOwner> = {};
