package inventory_ai_service.service.impl;

import com.services.dtos.InventoryDTO;
import com.services.dtos.MovementDTO;
import inventory_ai_service.dto.PredictionResponse;
import inventory_ai_service.feign.InventoryClient;
import inventory_ai_service.feign.MovementClient;
import inventory_ai_service.service.PredictionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

// Smile
import smile.data.DataFrame;
import smile.data.vector.DoubleVector;
import smile.data.formula.Formula;
import smile.regression.OLS;

@Service
public class PredictionServiceImpl implements PredictionService {

    private final InventoryClient inventoryClient;
    private final MovementClient movementClient;

    public PredictionServiceImpl(InventoryClient inventoryClient,
                                 MovementClient movementClient) {
        this.inventoryClient = inventoryClient;
        this.movementClient = movementClient;
    }

    @Override
    public PredictionResponse predict(Long productId) {

        System.out.println("===== IA DEBUG =====");
        System.out.println("Product ID: " + productId);

        try {
            // 📦 INVENTORY
            InventoryDTO product = inventoryClient.getInventory(productId);

            if (product == null) {
                throw new RuntimeException("Producto no encontrado");
            }

            System.out.println("Stock actual: " + product.getQuantity());

            // 📊 MOVEMENTS
            List<MovementDTO> movements = movementClient.getMovements(productId);

            if (movements == null || movements.isEmpty()) {
                System.out.println("No hay movimientos");

                return new PredictionResponse(
                        "SIN HISTORICO",
                        0,
                        LocalDate.now(),
                        0,
                        product.getQuantity()
                );
            }

            System.out.println("Total movimientos: " + movements.size());

            // 🔥 SOLO SALIDAS (consumo real)
            List<MovementDTO> consumptions = movements.stream()
                    .filter(m ->
                            m.getQuantityChange() != null &&
                                    m.getQuantityChange() < 0
                    )
                    .toList();

            System.out.println("Consumos detectados: " + consumptions.size());

            // 🚨 SI NO HAY CONSUMO
            if (consumptions.isEmpty()) {
                return new PredictionResponse(
                        "SIN CONSUMO",
                        0,
                        LocalDate.now(),
                        0,
                        product.getQuantity()
                );
            }

            int n = consumptions.size();

            double[] x = new double[n];
            double[] y = new double[n];

            for (int i = 0; i < n; i++) {
                x[i] = i;
                Integer quantity = consumptions.get(i).getQuantityChange();
                if (quantity == null) {
                    quantity = 0;
                }
                y[i] = Math.abs(quantity);            }

            double predictedConsumption;

            // 🤖 INTENTAR MODELO
            try {
                DataFrame df = DataFrame.of(
                        DoubleVector.of("x", x),
                        DoubleVector.of("y", y)
                );

                var model = OLS.fit(Formula.lhs("y"), df);

                // 🔥 FIX IMPORTANTE (intercepto)
                predictedConsumption = model.predict(new double[]{1, n});

                System.out.println("Predicción modelo: " + predictedConsumption);

            } catch (Exception e) {
                System.out.println("Error modelo, usando promedio");
                predictedConsumption = 0;
            }

            // 🔥 FALLBACK SI EL MODELO FALLA
            if (predictedConsumption <= 0 || Double.isNaN(predictedConsumption)) {
                predictedConsumption = consumptions.stream()
                        .mapToDouble(m -> Math.abs(m.getQuantityChange()))
                        .average()
                        .orElse(1);

                System.out.println("Predicción fallback (promedio): " + predictedConsumption);
            }

            // 📉 DÍAS RESTANTES
            double daysRemaining = product.getQuantity() / predictedConsumption;

            if (Double.isInfinite(daysRemaining) || Double.isNaN(daysRemaining)) {
                daysRemaining = 0;
            }

            LocalDate depletionDate = LocalDate.now().plusDays((long) daysRemaining);

            // 🚨 ALERTAS
            String alert;
            if (daysRemaining <= 7) alert = "CRITICO";
            else if (daysRemaining <= 15) alert = "ADVERTENCIA";
            else alert = "OK";

            // 📦 STOCK RECOMENDADO
            int recommendedStock = (int) Math.ceil(predictedConsumption * 10);

            return new PredictionResponse(
                    alert,
                    daysRemaining,
                    depletionDate,
                    predictedConsumption,
                    recommendedStock
            );

        } catch (Exception e) {

            e.printStackTrace(); // 🔥 IMPORTANTE

            return new PredictionResponse(
                    "ERROR",
                    0,
                    LocalDate.now(),
                    0,
                    0
            );
        }
    }
}