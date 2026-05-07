import { useState } from "react";
import { useForm } from "react-hook-form";
import { InventoryService } from "../services/Inventory";
import type { Inventory } from "../entities/Inventory";

type Props = {
  onClose: () => void;
  onSuccess: () => void;
  item?: Inventory | null; // 👈 NUEVO
};

type InventoryFormType = {
  name: string;
  category: string;
  unit: string;
  quantity: number;
  minStock: number;
  lotNumber: string;
  expirationDate: string;
  unitCost: number;
};


export default function InventoryForm({ onClose, onSuccess }: Props) {
  const { register, handleSubmit, formState: { errors } } = useForm<InventoryFormType>();

  const [file, setFile] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  // 🔥 CONVERTIR FILE A BASE64
  const toBase64 = (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = error => reject(error);
    });
  };

  const onSubmit = async (data: InventoryFormType) => {
    try {
      setLoading(true);
      setErrorMsg("");

      let imageUrl = "";

      if (file) {
        imageUrl = await toBase64(file);
      }

      const payload = {
        ...data,
        imageUrl
      };

      await InventoryService.create(payload);

      onSuccess();

    } catch (error: any) {
      console.error("Error creando producto", error);

      // 🔥 CAPTURAR ERROR DEL BACKEND
      if (error.response?.data?.message) {
        setErrorMsg(error.response.data.message);
      } else {
        setErrorMsg("el producto ya existe o hubo un error en el servidor");
      }

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">

      <div className="bg-white w-125 rounded-2xl shadow-2xl overflow-hidden">

        {/* HEADER */}
        <div className="bg-linear-to-r from-blue-600 to-blue-500 p-5">
          <h2 className="text-white text-xl font-bold">
            Nuevo Producto
          </h2>
          <p className="text-blue-100 text-sm">
            Agrega un nuevo insumo al inventario
          </p>
        </div>

        {/* FORM */}
        <form onSubmit={handleSubmit(onSubmit)} className="p-6 space-y-4">

          <div className="grid grid-cols-2 gap-4">

            <div>
              <input
                className="input"
                placeholder="Nombre"
                {...register("name", { required: "El nombre es obligatorio" })}
              />
              {errors.name && <p className="error">{errors.name.message}</p>}
            </div>

            <div>
              <input
                className="input"
                placeholder="Categoría"
                {...register("category", { required: "La categoría es obligatoria" })}
              />
              {errors.category && <p className="error">{errors.category.message}</p>}
            </div>

            <div>
              <input
                className="input"
                placeholder="Unidad (g, ml...)"
                {...register("unit", { required: "La unidad es obligatoria" })}
              />
              {errors.unit && <p className="error">{errors.unit.message}</p>}
            </div>

            <div>
              <input
                className="input"
                type="number"
                placeholder="Cantidad"
                {...register("quantity", { required: "Cantidad requerida", min: 0 })}
              />
              {errors.quantity && <p className="error">Cantidad inválida</p>}
            </div>

            <div>
              <input
                className="input"
                type="number"
                placeholder="Stock mínimo"
                {...register("minStock", { required: "Stock mínimo requerido", min: 0 })}
              />
              {errors.minStock && <p className="error">Valor inválido</p>}
            </div>

            <div>
              <input
                className="input"
                placeholder="Lote"
                {...register("lotNumber")}
              />
            </div>

            <input
              className="input col-span-2"
              type="date"
              {...register("expirationDate")}
            />

            <div className="col-span-2">
              <input
                className="input"
                type="number"
                placeholder="Costo unitario"
                {...register("unitCost", { required: "Costo requerido", min: 0 })}
              />
              {errors.unitCost && <p className="error">Valor inválido</p>}
            </div>

          </div>

          {/* FILE INPUT */}
          <div className="bg-gray-50 p-3 rounded-lg border">
            <label className="text-sm font-medium text-gray-700">
              Imagen del producto
            </label>

            <input
              type="file"
              accept="image/*"
              className="w-full mt-2 text-sm"
              onChange={(e) => {
                if (e.target.files && e.target.files.length > 0) {
                  setFile(e.target.files[0]);
                }
              }}
            />
          </div>

          {/* 🔥 ERROR BACKEND */}
          {errorMsg && (
            <div className="bg-red-100 text-red-700 p-3 rounded-lg text-sm">
              {errorMsg}
            </div>
          )}

          {/* BUTTONS */}
          <div className="flex justify-end gap-3 pt-4">

            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 rounded-lg border text-gray-600 hover:bg-gray-100"
            >
              Cancelar
            </button>

            <button
              type="submit"
              disabled={loading}
              className="px-5 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-50"
            >
              {loading ? "Guardando..." : "Guardar"}
            </button>

          </div>

        </form>

      </div>

      {/* STYLES */}
      <style>{`
        .input {
          width: 100%;
          padding: 10px;
          border: 1px solid #e5e7eb;
          border-radius: 10px;
          font-size: 14px;
          outline: none;
          transition: all 0.2s ease;
        }

        .input:focus {
          border-color: #2563eb;
          box-shadow: 0 0 0 3px rgba(37,99,235,0.2);
        }

        .error {
          color: red;
          font-size: 12px;
          margin-top: 4px;
        }
      `}</style>

    </div>
  );
}