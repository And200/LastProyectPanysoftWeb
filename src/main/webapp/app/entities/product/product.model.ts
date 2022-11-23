import { IDetailSale } from 'app/entities/detail-sale/detail-sale.model';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { IDetailAmountRecip } from 'app/entities/detail-amount-recip/detail-amount-recip.model';
import { IDetailOrder } from 'app/entities/detail-order/detail-order.model';
import { ICategory } from 'app/entities/category/category.model';
import { IProvider } from 'app/entities/provider/provider.model';
import { IPresentation } from 'app/entities/presentation/presentation.model';

export interface IProduct {
  id?: number;
  productName?: string;
  buyPrice?: number;
  detailSales?: IDetailSale[] | null;
  inventories?: IInventory[] | null;
  detailAmountRecips?: IDetailAmountRecip[] | null;
  detailOrders?: IDetailOrder[] | null;
  category?: ICategory;
  provider?: IProvider;
  presentation?: IPresentation;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string,
    public buyPrice?: number,
    public detailSales?: IDetailSale[] | null,
    public inventories?: IInventory[] | null,
    public detailAmountRecips?: IDetailAmountRecip[] | null,
    public detailOrders?: IDetailOrder[] | null,
    public category?: ICategory,
    public provider?: IProvider,
    public presentation?: IPresentation
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
