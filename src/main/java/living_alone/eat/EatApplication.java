package living_alone.eat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EatApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatApplication.class, args);
	}

}
