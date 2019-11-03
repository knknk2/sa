import { Moment } from 'moment';
import { IBo2mOwner } from 'app/shared/model/bo-2-m-owner.model';

export interface IBo2mCar {
  id?: number;
  name?: string;
  createdAt?: Moment;
  bo2mOwner?: IBo2mOwner;
}

export const defaultValue: Readonly<IBo2mCar> = {};
