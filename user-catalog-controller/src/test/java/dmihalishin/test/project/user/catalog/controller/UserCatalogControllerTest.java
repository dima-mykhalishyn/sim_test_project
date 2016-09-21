package dmihalishin.test.project.user.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmihalishin.test.project.user.catalog.controller.support.GlobalExceptionHandler;
import dmihalishin.test.project.user.catalog.core.UserCatalogLogic;
import dmihalishin.test.project.user.catalog.model.UserDTO;
import dmihalishin.test.project.user.catalog.core.exception.ValidationException;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

/**
 * Tests for {@link UserCatalogController}
 *
 * @author dmihalishin@gmail.com
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserCatalogController.class)
public class UserCatalogControllerTest {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
	}

	@Test
	public void getById() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
				.andExpect(MockMvcResultMatchers.content()
						.string("{\"response\":{\"id\":1,\"username\":\"test\",\"firstName\":\"a\",\"lastName\":\"b\"}}"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void create() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put("/user")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(UserDTO.newInstance(null, "test", "a", "b"))))
				.andExpect(MockMvcResultMatchers.content()
						.string("{\"response\":{\"id\":1,\"username\":\"test\",\"firstName\":\"a\",\"lastName\":\"b\"}}"))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void createError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.put("/user")
				.contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(UserDTO.newInstance(null, "InValid", "a", "b"))))
				.andExpect(MockMvcResultMatchers.content()
						.string("{\"errors\":[\"Validation error\"]}"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Configuration
	@EnableWebMvc
	public static class TestConfiguration extends WebMvcConfigurerAdapter {

		private final UserDTO dto = UserDTO.newInstance(1L, "test", "a", "b");

		@Override
		public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
			converters.add(new MappingJackson2HttpMessageConverter());
		}

		@Bean
		public UserCatalogController controller(final UserCatalogLogic logic) {
			return new UserCatalogController(logic);
		}

		@SuppressWarnings("unchecked")
		@Bean
		public UserCatalogLogic userCatalogLogic() {
			final UserCatalogLogic logic = EasyMock.createMock(UserCatalogLogic.class);
			EasyMock.expect(logic.getById(EasyMock.anyObject(Long.class))).andReturn(dto);
			EasyMock.expect(logic.saveOrUpdate(EasyMock.anyObject(UserDTO.class))).andReturn(dto);
			EasyMock.expect(logic.saveOrUpdate(EasyMock.anyObject(UserDTO.class))).andThrow(new ValidationException("Validation error"));
			EasyMock.replay(logic);
			return logic;
		}

		@Bean
		public GlobalExceptionHandler globalExceptionHandler() {
			return new GlobalExceptionHandler();
		}

	}

}