import type { Inventory } from "../entities/Inventory";

const BASE_URL = "http://localhost:8090/api/inventory"; 

const getToken = () => localStorage.getItem("token");

export const InventoryService = {

  async getAll(): Promise<Inventory[]> {
    const res = await fetch(BASE_URL, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
        "Content-Type": "application/json"
      }
    });

    if (!res.ok) throw new Error("Error al cargar inventario");

    return res.json();
  },

  async create(data: Inventory): Promise<Inventory> {
    const res = await fetch(BASE_URL, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${getToken()}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error("Error creando inventario");

    return res.json();
  }
};