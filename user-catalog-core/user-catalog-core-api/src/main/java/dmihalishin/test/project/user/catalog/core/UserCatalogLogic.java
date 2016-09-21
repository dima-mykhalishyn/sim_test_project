package dmihalishin.test.project.user.catalog.core;

import dmihalishin.test.project.user.catalog.model.UserDTO;

/**
 * User Catalog Logic API
 *
 * @author dmihalishin@gmail.com
 */
public interface UserCatalogLogic {

	/**
	 * Retrieves the {@link UserDTO} by ID
	 *
	 * @param id the user id
	 * @return the {@code UserDTO}
	 */
	UserDTO getById(Long id);

	/**
	 * Save or update the {@link UserDTO}
	 *
	 * @param dto the user DTO. Cannot be {@code null}
	 * @return the saved {@code UserDTO}
	 */
	UserDTO saveOrUpdate(UserDTO dto);

}
