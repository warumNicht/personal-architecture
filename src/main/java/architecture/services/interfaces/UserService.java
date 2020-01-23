package architecture.services.interfaces;

import architecture.domain.models.serviceModels.UserServiceModel;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);
}
