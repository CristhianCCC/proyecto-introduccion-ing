package inventory_ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InventoryAiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryAiServiceApplication.class, args);
	}

}
