import { IPurchaseReceipt } from 'app/entities/purchase-receipt/purchase-receipt.model';
import { IProduct } from 'app/entities/product/product.model';
import { IPresentation } from 'app/entities/presentation/presentation.model';

export interface IDetailSale {
  id?: number;
  productAmount?: number;
  purchaseReceipts?: IPurchaseReceipt[] | null;
  product?: IProduct;
  presentation?: IPresentation;
}

export class DetailSale implements IDetailSale {
  constructor(
    public id?: number,
    public productAmount?: number,
    public purchaseReceipts?: IPurchaseReceipt[] | null,
    public product?: IProduct,
    public presentation?: IPresentation
  ) {}
}

export function getDetailSaleIdentifier(detailSale: IDetailSale): number | undefined {
  return detailSale.id;
}
