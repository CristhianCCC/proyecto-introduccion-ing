import { useEffect, useState } from "react";
import type { Inventory } from "../entities/Inventory";
import { InventoryService } from "../services/Inventory";
import { motion } from "framer-motion";
import InventoryForm from "./InventoryForm";
import InventoryAIComponent from "./InventoryAIComponent";
import MovementForm from "./MovementForm";

export default function InventoryPage() {
  const [items, setItems] = useState<Inventory[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [open, setOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState<Inventory | null>(null);

  // 🤖 IA
  const [selectedProductId, setSelectedProductId] = useState<number | null>(null);

  // 📦 Movimientos
  const [movementOpen, setMovementOpen] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const data = await InventoryService.getAll();
      setItems(data);
    } catch (err) {
      console.error(err);
      setError("No se pudo cargar el inventario");
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (item: Inventory) => {
    setSelectedItem(item);
    setOpen(true);
  };

  const handleCreate = () => {
    setSelectedItem(null);
    setOpen(true);
  };

  return (
    <div className="min-h-screen bg-linear-to-br from-blue-600 to-blue-800 p-6">
      <div className="bg-white rounded-2xl shadow-xl p-6">

        {/* HEADER */}
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-700">
            📦 Inventory Dashboard
          </h1>

          <button
            onClick={handleCreate}
            className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition"
          >
            + Nuevo Producto
          </button>
        </div>

        {/* STATES */}
        {loading && <p className="text-gray-500 animate-pulse">Cargando...</p>}
        {error && <p className="text-red-500">{error}</p>}

        {/* TABLE */}
        <div className="overflow-x-auto">
          <table className="w-full border rounded-lg overflow-hidden">

            <thead className="bg-gray-100 text-gray-600">
              <tr>
                <th className="p-2">Imagen</th>
                <th className="p-2">Nombre</th>
                <th className="p-2">Categoría</th>
                <th className="p-2">Cantidad</th>
                <th className="p-2">Stock Min</th>
                <th className="p-2">Costo</th>
                <th className="p-2">Acciones</th>
                <th className="p-2">Movimientos</th>
              </tr>
            </thead>

            <tbody>
              {items.map((item) => (
                <motion.tr
                  key={item.id}
                  initial={{ opacity: 0, y: 5 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.3 }}
                  onClick={() => {
                    if (item.id != null) {
                      setSelectedProductId(item.id);
                    }
                  }}
                  className={`text-center border-t hover:bg-gray-50 cursor-pointer transition ${
                    selectedProductId === item.id ? "bg-blue-100" : ""
                  }`}
                >
                  {/* Imagen */}
                  <td className="p-2">
                    {item.imageUrl ? (
                      <img
                        src={item.imageUrl}
                        className="w-16 h-16 object-cover rounded-lg shadow mx-auto"
                      />
                    ) : (
                      "N/A"
                    )}
                  </td>

                  <td>{item.name}</td>
                  <td>{item.category}</td>
                  <td className="font-semibold">{item.quantity}</td>
                  <td>{item.minStock}</td>
                  <td>${item.unitCost}</td>

                  {/* EDITAR */}
                  <td>
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        handleEdit(item);
                      }}
                      className="bg-yellow-500 text-white px-3 py-1 rounded-lg hover:bg-yellow-600 text-sm transition"
                    >
                      Editar
                    </button>
                  </td>

                  {/* MOVIMIENTOS */}
                  <td>
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        if (item.id != null) {
                          setSelectedProductId(item.id);
                          setMovementOpen(true);
                        }
                      }}
                      className="bg-green-600 text-white px-3 py-1 rounded-lg hover:bg-green-700 text-sm transition"
                    >
                      Movimientos
                    </button>
                  </td>

                </motion.tr>
              ))}
            </tbody>

          </table>
        </div>

        {/* 🤖 IA PANEL */}
        {selectedProductId !== null && (
          <div className="mt-6">
            <InventoryAIComponent productId={selectedProductId} />
          </div>
        )}

      </div>

      {/* 📦 MODAL MOVIMIENTOS */}
      {movementOpen && selectedProductId !== null && (
        <MovementForm
          productId={selectedProductId}
          onClose={() => setMovementOpen(false)}
          onSuccess={() => {
            setMovementOpen(false);
            loadData();
          }}
        />
      )}

      {/* ✏️ MODAL INVENTORY */}
      {open && (
        <InventoryForm
          item={selectedItem}
          onClose={() => setOpen(false)}
          onSuccess={() => {
            setOpen(false);
            loadData();
          }}
        />
      )}
    </div>
  );
}