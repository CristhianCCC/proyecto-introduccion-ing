package consumption_service.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consumption")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;     // ID del producto (viene de inventory)
    private Integer quantity;  // Cantidad consumida
    private LocalDate date;    // Fecha del consumo

    public Consumption() {}

    public Consumption(Long id, Long productId, Integer quantity, LocalDate date) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}