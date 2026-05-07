import { useState } from "react";

interface Props {
  productId: number;
  onClose: () => void;
  onSuccess: () => void;
}

export default function MovementForm({ productId, onClose, onSuccess }: Props) {
  const [quantity, setQuantity] = useState(0);
  const [type, setType] = useState("IN");

  const handleSubmit = async () => {
    try {
        await fetch("http://localhost:8090/api/movements", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            productId,
            quantityChange: type === "OUT" ? -quantity : quantity,
            type,
        }),
        });

        onSuccess();
    } catch (error) {
        console.error(error);
    }
    };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-center">
      <div className="bg-white p-6 rounded-xl w-96">

        <h2 className="text-lg font-bold mb-4">
          Registrar Movimiento
        </h2>

        <select
          value={type}
          onChange={(e) => setType(e.target.value)}
          className="w-full mb-3 border p-2 rounded"
        >
          <option value="IN">➕ Entrada</option>
          <option value="OUT">➖ Salida</option>
        </select>

        <input
          type="number"
          placeholder="Cantidad"
          value={quantity}
          onChange={(e) => setQuantity(Number(e.target.value))}
          className="w-full mb-3 border p-2 rounded"
        />

        <div className="flex justify-end gap-2">
          <button
            onClick={onClose}
            className="bg-gray-400 text-white px-3 py-1 rounded"
          >
            Cancelar
          </button>

          <button
            onClick={handleSubmit}
            className="bg-blue-600 text-white px-3 py-1 rounded"
          >
            Guardar
          </button>
        </div>

      </div>
    </div>
  );
}