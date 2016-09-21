package dmihalishin.test.project.user.catalog.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * DB Configuration
 *
 * @author dmihalishin@gmail.com
 */
@SpringBootConfiguration
@ComponentScan(value = {
		"dmihalishin.test.project.user.catalog"
})
@EntityScan("dmihalishin.test.project.user.catalog.domain")
@EnableJpaRepositories("dmihalishin.test.project.user.catalog.repository")
public class DBConfiguration {

}