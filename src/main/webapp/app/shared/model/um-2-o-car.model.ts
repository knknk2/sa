import { IUm2oOwner } from 'app/shared/model/um-2-o-owner.model';

export interface IUm2oCar {
  id?: number;
  name?: string;
  um2oOwner?: IUm2oOwner;
}

export const defaultValue: Readonly<IUm2oCar> = {};
