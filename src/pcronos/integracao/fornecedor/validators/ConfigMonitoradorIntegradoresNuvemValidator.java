package pcronos.integracao.fornecedor.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import pcronos.integracao.fornecedor.annotations.ValidConfigMonitoradorIntegradoresNuvem;
import pcronos.integracao.fornecedor.entidades.ConfigMonitoradorIntegradoresNuvem;

public class ConfigMonitoradorIntegradoresNuvemValidator implements ConstraintValidator<ValidConfigMonitoradorIntegradoresNuvem, ConfigMonitoradorIntegradoresNuvem> {

    @Override
    public void initialize(final ValidConfigMonitoradorIntegradoresNuvem constraintAnnotation) {

    }

    @Override
    public boolean isValid(final ConfigMonitoradorIntegradoresNuvem value,
                             final ConstraintValidatorContext context) {
    	if (value.IdContatoTiSecundarioIntegrador != null && value.IdContatoTiSecundarioIntegrador == value.IdContatoTiIntegrador)
    		return false;
    	else
            return true;
    }
}