import { IM2mDriverDTOMF } from 'app/shared/model/m-2-m-driver-dtomf.model';

export interface IM2mCarDTOMF {
  id?: number;
  name?: string;
  m2mDriverDTOMFS?: IM2mDriverDTOMF[];
}

export const defaultValue: Readonly<IM2mCarDTOMF> = {};
