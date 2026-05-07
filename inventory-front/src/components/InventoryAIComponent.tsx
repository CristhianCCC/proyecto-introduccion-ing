import { useEffect, useState } from "react";
import type { Prediction } from "../entities/Prediction";
import { getPrediction } from "../services/InventoryIA";


interface Props {  productId: number; }

const InventoryAIComponent: React.FC<Props> = ({ productId }) => {

  const [prediction, setPrediction] = useState<Prediction | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchPrediction = async () => {
    try {
      const data = await getPrediction(productId);
      setPrediction(data);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPrediction();
  }, [productId]);

  if (loading) return <p className="text-gray-500 animate-pulse">Cargando IA...</p>;
  if (!prediction) return <p className="text-red-500">No hay datos</p>;

  function getColor(alert: string): string {
  switch (alert) {
    case "CRITICO":
      return "bg-red-500";
    case "ADVERTENCIA":
      return "bg-yellow-500";
    default:
      return "bg-green-500";
  }
}

  return (
    <div className="flex justify-center">
    <div className="mt-6 p-5 rounded-2xl bg-linear-to-br from-white to-gray-50 shadow-xl border w-full max-w-md">

      <h3 className="text-xl font-bold mb-4 flex items-center gap-2">
        🤖 Predicción Inteligente
      </h3>

      {/* ALERTA */}
      <div className={`text-white text-center font-bold py-2 rounded-lg mb-4 shadow ${getColor(prediction.alert)}`}>
        {prediction.alert}
      </div>

      {/* DATOS */}
      <div className="space-y-2 text-gray-700">

        <div className="flex justify-between">
          <span>📅 Días restantes</span>
          <span className="font-semibold">
            {prediction.daysRemaining?.toFixed(2) ?? "N/A"}
          </span>
        </div>

        <div className="flex justify-between">
          <span>📉 Consumo diario</span>
          <span className="font-semibold">
            {prediction.predictedDailyConsumption?.toFixed(2) ?? "N/A"}
          </span>
        </div>

        <div className="flex justify-between">
          <span>📦 Stock recomendado</span>
          <span className="font-semibold">
            {prediction.recommendedStock ?? "N/A"}
          </span>
        </div>

        <div className="flex justify-between">
          <span>⏳ Se agota</span>
          <span className="font-semibold text-sm">
            {prediction.depletionDate}
          </span>
        </div>

      </div>

    </div>
    </div>
  );
};

export default InventoryAIComponent;