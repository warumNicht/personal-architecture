package architecture.web.controllers;

import architecture.domain.CountryCodes;
import architecture.domain.models.bindingModels.ImageEditBindingModel;
import architecture.domain.models.serviceModels.ImageServiceModel;
import architecture.services.interfaces.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping(value = "/admin/images")
public class ImageController extends BaseController{
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
        for (CountryCodes value : CountryCodes.values()) {
            imageToEdit.getLocalImageNames().putIfAbsent(value, "");
        }

        modelAndView.addObject("imageEdit", imageToEdit);
        modelAndView.setViewName("edit-image");
        return modelAndView;
    }

    @PutMapping(value = "/edit/{imageId}")
    public String editImagePut(@ModelAttribute(name = "imageEdit") ImageEditBindingModel model,
                               @PathVariable(name = "imageId") Long imageId){
        model.getLocalImageNames().entrySet().removeIf(kv->kv.getValue().isEmpty());
        ImageServiceModel imageById = this.imageService.getImageById(imageId);
        imageById.setUrl(model.getUrl());
        imageById.setLocalImageNames(model.getLocalImageNames());
        this.imageService.saveImage(imageById);
        return "redirect:/" + super.getLocale() + "/admin/articles/edit/" + imageById.getArticle().getId();
    }
}
