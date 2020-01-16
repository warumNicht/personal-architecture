package architecture.annotations;

import architecture.constants.AppConstants;
import architecture.domain.models.bindingModels.ImageBindingModel;
import architecture.util.ValidationUtilImpl;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class ImageBindingValidWhenEmptyImpl implements ConstraintValidator<ImageBindingValidWhenEmpty, ImageBindingModel> {
    @Override
    public boolean isValid(ImageBindingModel value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        if(value.getUrl()==null){
            context.buildConstraintViolationWithTemplate("Not null")
                    .addPropertyNode("url")
                    .addConstraintViolation();
        }
        if(value.getName()==null){
            context.buildConstraintViolationWithTemplate("Not null")
                    .addPropertyNode("name")
                    .addConstraintViolation();
        }
        if(value.getName()!=null && value.getName().isEmpty()&&value.getUrl().isEmpty()){
            return true;
        }
        if(value.getUrl().isEmpty()){
            context.buildConstraintViolationWithTemplate("{text.empty}")
                    .addPropertyNode("url")
                    .addConstraintViolation();
        }
        if(value.getUrl().length()< AppConstants.NAME_MIN_LENGTH){

            HibernateConstraintValidatorContext hibernateContext =
                    context.unwrap( HibernateConstraintValidatorContext.class );

            hibernateContext.disableDefaultConstraintViolation();
            hibernateContext.addMessageParameter("min", AppConstants.NAME_MIN_LENGTH)
                    .buildConstraintViolationWithTemplate( "{text.length.min}" )
                    .addPropertyNode("url")
                    .addConstraintViolation();
        }


//        if(value.getUrl().isEmpty())
//        if(value.getName().isEmpty()&&value.getUrl().isEmpty()){
//            return true;
//        }
//        boolean valid = new ValidationUtilImpl().isValid(value);
        return false;
    }
}
