import { ITo2mCarInf } from 'app/shared/model/to-2-m-car-inf.model';

export interface ITo2mPersonInf {
  id?: number;
  name?: string;
  to2mOwnedCarInfs?: ITo2mCarInf[];
  to2mDrivedCarInfs?: ITo2mCarInf[];
}

export const defaultValue: Readonly<ITo2mPersonInf> = {};
