import { IO2oCar } from 'app/shared/model/o-2-o-car.model';

export interface IO2oDriver {
  id?: number;
  name?: string;
  o2oCar?: IO2oCar;
}

export const defaultValue: Readonly<IO2oDriver> = {};
