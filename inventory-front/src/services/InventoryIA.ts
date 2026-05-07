import type { Prediction } from "../entities/Prediction";

const API_AI = "http://localhost:8090/ai";

export const getPrediction = async (productId: number): Promise<Prediction> => {
  const res = await fetch(`${API_AI}/predict/${productId}`); 

  if (!res.ok) {
    throw new Error("Error IA");
  }

  return res.json();
};