import dayjs from 'dayjs/esm';
import { IProvider } from 'app/entities/provider/provider.model';
import { IOrderPlaced } from 'app/entities/order-placed/order-placed.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IDetailOrder {
  id?: number;
  quantityOrdered?: number;
  date?: dayjs.Dayjs;
  invoiceNumber?: string;
  pricePayment?: number;
  provider?: IProvider;
  orderPlaced?: IOrderPlaced;
  product?: IProduct;
}

export class DetailOrder implements IDetailOrder {
  constructor(
    public id?: number,
    public quantityOrdered?: number,
    public date?: dayjs.Dayjs,
    public invoiceNumber?: string,
    public pricePayment?: number,
    public provider?: IProvider,
    public orderPlaced?: IOrderPlaced,
    public product?: IProduct
  ) {}
}

export function getDetailOrderIdentifier(detailOrder: IDetailOrder): number | undefined {
  return detailOrder.id;
}
