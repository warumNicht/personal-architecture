package architecture.services;

import architecture.domain.entities.auth.Role;
import architecture.domain.entities.auth.User;
import architecture.domain.models.serviceModels.UserServiceModel;
import architecture.repositories.UserRepository;
import architecture.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public  void registerUser(UserServiceModel userServiceModel){
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.getRoles().add(new Role());
    }
}
