package dmihalishin.test.project.user.catalog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User domain entity
 *
 * @author dmihalishin@gmail.com
 * @see Serializable
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 6769317950880966435L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;

	@Column(name = "first_name", nullable = false, unique = true, length = 50)
	private String firstName;

	@Column(name = "last_name", nullable = false, unique = true, length = 50)
	private String lastName;

	public User() {
		// default one
	}

	public User(final Long id, final String username, final String firstName, final String lastName) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

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
}
