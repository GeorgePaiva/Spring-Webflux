package academy.devdojo.springwebfluxessentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class SpringWebfluxEssentialsApplication {

	static {
		BlockHound.install(); //BlockHound verifica o bloqueio de chamadas em lugares onde não deveria haver
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxEssentialsApplication.class, args);
	}

}
