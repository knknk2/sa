import { IM2mCar } from 'app/shared/model/m-2-m-car.model';

export interface IM2mDriver {
  id?: number;
  name?: string;
  m2mCars?: IM2mCar[];
}

export const defaultValue: Readonly<IM2mDriver> = {};
