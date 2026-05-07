package inventory_ai_service.dto;

import java.time.LocalDate;


public class PredictionResponse {

    private double predictedDailyConsumption;
    private double daysRemaining;
    private LocalDate depletionDate;
    private String alert;
    private int recommendedStock;

    public PredictionResponse() {  }

    public PredictionResponse(String alert, double daysRemaining, LocalDate depletionDate, double predictedDailyConsumption, int recommendedStock) {
        this.alert = alert;
        this.daysRemaining = daysRemaining;
        this.depletionDate = depletionDate;
        this.predictedDailyConsumption = predictedDailyConsumption;
        this.recommendedStock = recommendedStock;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public double getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(double daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public LocalDate getDepletionDate() {
        return depletionDate;
    }

    public void setDepletionDate(LocalDate depletionDate) {
        this.depletionDate = depletionDate;
    }

    public double getPredictedDailyConsumption() {
        return predictedDailyConsumption;
    }

    public void setPredictedDailyConsumption(double predictedDailyConsumption) {
        this.predictedDailyConsumption = predictedDailyConsumption;
    }

    public int getRecommendedStock() {
        return recommendedStock;
    }

    public void setRecommendedStock(int recommendedStock) {
        this.recommendedStock = recommendedStock;
    }
}