import { IBo2mCarDTO } from 'app/shared/model/bo-2-m-car-dto.model';

export interface IBo2mOwnerDTO {
  id?: number;
  name?: string;
  bo2mCarDTOS?: IBo2mCarDTO[];
}

export const defaultValue: Readonly<IBo2mOwnerDTO> = {};
