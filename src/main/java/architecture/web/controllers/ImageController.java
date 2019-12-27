package architecture.web.controllers;

import architecture.domain.models.bindingModels.ImageEditBindingModel;
import architecture.domain.models.serviceModels.ImageServiceModel;
import architecture.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/images")
public class ImageController {
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageController(ImageService imageService, ModelMapper modelMapper) {
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView editImage(@PathVariable Long id, ModelAndView modelAndView){
        ImageServiceModel imageById = this.imageService.getImageById(id);
        ImageEditBindingModel imageToEdit = this.modelMapper.map(imageById, ImageEditBindingModel.class);
        modelAndView.addObject("imageEdit", imageToEdit);
        modelAndView.setViewName("edit-image");
        return modelAndView;
    }
}
