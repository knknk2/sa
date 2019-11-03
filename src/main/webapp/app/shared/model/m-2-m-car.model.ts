import { IM2mDriver } from 'app/shared/model/m-2-m-driver.model';

export interface IM2mCar {
  id?: number;
  name?: string;
  m2mDrivers?: IM2mDriver[];
}

export const defaultValue: Readonly<IM2mCar> = {};
