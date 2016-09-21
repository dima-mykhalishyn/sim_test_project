package dmihalishin.test.project.user.catalog.controller;

import dmihalishin.test.project.user.catalog.model.BaseResponse;
import dmihalishin.test.project.user.catalog.core.UserCatalogLogic;
import dmihalishin.test.project.user.catalog.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Catalog Controller
 *
 * @author dmihalishin@gmail.com
 */
@RestController
public class UserCatalogController {

	public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";

	private UserCatalogLogic userCatalogLogic;

	@Autowired
	public UserCatalogController(final UserCatalogLogic userCatalogLogic) {
		this.userCatalogLogic = userCatalogLogic;
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = JSON_CONTENT_TYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public BaseResponse<UserDTO> getById(@PathVariable("id") final Long id) {
		final UserDTO dto = this.userCatalogLogic.getById(id);
		return new BaseResponse<>(dto);
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = JSON_CONTENT_TYPE, produces = JSON_CONTENT_TYPE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public BaseResponse<UserDTO> create(@RequestBody final UserDTO userDTO) {
		final UserDTO dto = this.userCatalogLogic.saveOrUpdate(userDTO);
		return new BaseResponse<>(dto);
	}

	// NOTE: there should be endpoints for update/delete,
	// but this is not in requirements for the project

}
