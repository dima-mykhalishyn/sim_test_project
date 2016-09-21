package dmihalishin.test.project.user.catalog.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * User DTO
 *
 * @author dmihalishin@gmail.com
 * @see Serializable
 */
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 3294894024212661859L;

	private Long id;

	@NotBlank
	@Length(max = 100)
	private String username;

	@NotBlank
	@Length(max = 50)
	private String firstName;

	@NotBlank
	@Length(max = 50)
	private String lastName;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public static UserDTO newInstance(final Long id, final String username, final String firstName, final String lastName) {
		final UserDTO dto = new UserDTO();
		dto.setId(id);
		dto.setUsername(username);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		return dto;
	}
}
