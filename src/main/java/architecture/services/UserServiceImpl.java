package architecture.services;

import architecture.domain.entities.auth.Role;
import architecture.domain.entities.auth.User;
import architecture.domain.entities.auth.UserRoles;
import architecture.domain.models.serviceModels.UserServiceModel;
import architecture.repositories.RoleRepository;
import architecture.repositories.UserRepository;
import architecture.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        Role roleUser = this.roleRepository.findRoleByAuthority(UserRoles.ROLE_USER);
        user.getAuthorities().add(roleUser);
        if (this.userRepository.count() == 0) {
            Role admin = this.roleRepository.findRoleByAuthority(UserRoles.ROLE_ADMIN);
            user.getAuthorities().add(admin);
        }
        this.userRepository.save(user);
        System.out.println();
//        user.getRoles().add(this.roleRepository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
        return user;
    }
}
