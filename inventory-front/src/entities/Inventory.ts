export interface Inventory {
  id?: number;
  name: string;
  category: string;
  unit: string;
  quantity: number;
  minStock: number;
  lotNumber: string;
  expirationDate: string;
  unitCost: number;
  imageUrl?: string;
}