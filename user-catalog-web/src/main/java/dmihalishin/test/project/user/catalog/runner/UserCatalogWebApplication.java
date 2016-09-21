package dmihalishin.test.project.user.catalog.runner;

import dmihalishin.test.project.user.catalog.config.DBConfiguration;
import dmihalishin.test.project.user.catalog.runner.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * User Catalog Web Application
 * @author dmihalishin@gmail.com
 */
@SpringBootApplication
@Import(value = {DBConfiguration.class, SwaggerConfig.class})
@ComponentScan
@EnableAutoConfiguration
public class UserCatalogWebApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(UserCatalogWebApplication.class, args);
	}

}
