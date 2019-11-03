import { IM2mCarDTOMF } from 'app/shared/model/m-2-m-car-dtomf.model';

export interface IM2mDriverDTOMF {
  id?: number;
  name?: string;
  m2mCarDTOMFS?: IM2mCarDTOMF[];
}

export const defaultValue: Readonly<IM2mDriverDTOMF> = {};
