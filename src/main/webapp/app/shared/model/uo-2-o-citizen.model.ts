import { IUo2oPassport } from 'app/shared/model/uo-2-o-passport.model';

export interface IUo2oCitizen {
  id?: number;
  name?: string;
  uo2oPassport?: IUo2oPassport;
}

export const defaultValue: Readonly<IUo2oCitizen> = {};
