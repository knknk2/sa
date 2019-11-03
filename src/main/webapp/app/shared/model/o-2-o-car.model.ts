import { IO2oDriver } from 'app/shared/model/o-2-o-driver.model';

export interface IO2oCar {
  id?: number;
  name?: string;
  o2oDriver?: IO2oDriver;
}

export const defaultValue: Readonly<IO2oCar> = {};
