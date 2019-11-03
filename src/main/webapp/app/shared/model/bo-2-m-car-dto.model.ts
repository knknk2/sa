import { Moment } from 'moment';

export interface IBo2mCarDTO {
  id?: number;
  name?: string;
  createdAt?: Moment;
  bo2mOwnerDTOId?: number;
}

export const defaultValue: Readonly<IBo2mCarDTO> = {};
