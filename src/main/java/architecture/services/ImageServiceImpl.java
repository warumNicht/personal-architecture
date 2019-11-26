package architecture.services;

import architecture.domain.entities.Image;
import architecture.domain.models.serviceModels.ImageServiceModel;
import architecture.repositories.ImageRepository;
import architecture.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveImage(ImageServiceModel imageServiceModel){
        Image image = this.modelMapper.map(imageServiceModel, Image.class);
        this.imageRepository.saveAndFlush(image);
        System.out.println();
    }
}
