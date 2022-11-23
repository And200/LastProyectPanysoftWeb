import { IProduct } from 'app/entities/product/product.model';

export interface IInventory {
  id?: number;
  stocks?: number;
  product?: IProduct;
}

export class Inventory implements IInventory {
  constructor(public id?: number, public stocks?: number, public product?: IProduct) {}
}

export function getInventoryIdentifier(inventory: IInventory): number | undefined {
  return inventory.id;
}
