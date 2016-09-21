package dmihalishin.test.project.user.catalog.service;

import dmihalishin.test.project.user.catalog.domain.User;
import dmihalishin.test.project.user.catalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User Repository
 *
 * @author dmihalishin@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see UserService#getById(long)
	 */
	@Override
	public Optional<User> getById(final long id) {
		return Optional.ofNullable(this.userRepository.findOne(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see UserService#getByUsername(String)
	 */
	@Override
	public List<User> getByUsername(final String username) {
		return this.userRepository.findByUsername(username);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see UserService#saveOrUpdate(User)
	 */
	@Transactional
	@Override
	public User saveOrUpdate(final User user) {
		return this.userRepository.save(user);
	}
}
