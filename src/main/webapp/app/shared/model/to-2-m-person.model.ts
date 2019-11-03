import { ITo2mCar } from 'app/shared/model/to-2-m-car.model';

export interface ITo2mPerson {
  id?: number;
  name?: string;
  to2mOwnedCars?: ITo2mCar[];
  to2mDrivedCars?: ITo2mCar[];
}

export const defaultValue: Readonly<ITo2mPerson> = {};
