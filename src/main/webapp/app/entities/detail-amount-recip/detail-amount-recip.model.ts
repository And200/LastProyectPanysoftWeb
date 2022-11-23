import { IProduct } from 'app/entities/product/product.model';
import { IRecip } from 'app/entities/recip/recip.model';

export interface IDetailAmountRecip {
  id?: number;
  amountProduct?: number;
  product?: IProduct;
  recip?: IRecip;
}

export class DetailAmountRecip implements IDetailAmountRecip {
  constructor(public id?: number, public amountProduct?: number, public product?: IProduct, public recip?: IRecip) {}
}

export function getDetailAmountRecipIdentifier(detailAmountRecip: IDetailAmountRecip): number | undefined {
  return detailAmountRecip.id;
}
