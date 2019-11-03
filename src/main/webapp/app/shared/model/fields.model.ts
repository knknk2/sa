import { Moment } from 'moment';
import { Enum1 } from 'app/shared/model/enumerations/enum-1.model';

export interface IFields {
  id?: number;
  str?: string;
  num1?: number;
  num2?: number;
  num3?: number;
  num4?: number;
  num5?: number;
  date1?: Moment;
  date2?: Moment;
  date3?: Moment;
  date4?: number;
  uuid?: string;
  bool?: boolean;
  enumeration?: Enum1;
  blobContentType?: string;
  blob?: any;
  blob2ContentType?: string;
  blob2?: any;
  blob3?: any;
}

export const defaultValue: Readonly<IFields> = {
  bool: false
};
