import { IDetailAmountRecip } from 'app/entities/detail-amount-recip/detail-amount-recip.model';

export interface IRecip {
  id?: number;
  nameRecip?: string;
  estimatedPricePreparation?: number;
  detailAmountRecips?: IDetailAmountRecip[] | null;
}

export class Recip implements IRecip {
  constructor(
    public id?: number,
    public nameRecip?: string,
    public estimatedPricePreparation?: number,
    public detailAmountRecips?: IDetailAmountRecip[] | null
  ) {}
}

export function getRecipIdentifier(recip: IRecip): number | undefined {
  return recip.id;
}
