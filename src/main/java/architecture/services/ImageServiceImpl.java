package architecture.services;

import architecture.domain.models.serviceModels.ImageServiceModel;
import architecture.repositories.ImageRepository;
import architecture.services.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveImage(ImageServiceModel imageServiceModel){
        System.out.println();
    }
}
