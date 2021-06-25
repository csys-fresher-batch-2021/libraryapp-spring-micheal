package in.reno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan(basePackages = "in.reno")
public class Spring2Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring2Application.class, args);
	}

}
