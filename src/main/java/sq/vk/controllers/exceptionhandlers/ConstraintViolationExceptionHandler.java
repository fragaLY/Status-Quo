package sq.vk.controllers.exceptionhandlers;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sq.vk.controllers.exceptionhandlers.errordetails.ErrorDetails;

/**
 * Created by Vadzim_Kavalkou on 4/20/2017.
 */
@ControllerAdvice
@PropertySources(@PropertySource("classpath:i18n/validation_errors_en.properties"))
public class ConstraintViolationExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

  @Autowired
  private Environment env;

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<?> handleConstraintViolationException(
    ConstraintViolationException ex,
    WebRequest req) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final Set<ConstraintViolation<?>> cvs = ex.getConstraintViolations();
    final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    LOG.info("ConstraintViolation errors were found: ");

    Set<ErrorDetails> eds = Sets.newConcurrentHashSet();

    for (ConstraintViolation ignored : cvs) {

      final String path = ex.getClass().getSimpleName().toLowerCase();
      final String message = env.getProperty(path);

      ErrorDetails ed = new ErrorDetails.Builder()
                          .setPropertyPath(path)
                          .setDeveloperMessage(ex.getClass().toString())
                          .setStatus(badRequest)
                          .setOutputMessage(message)
                        .build();

      LOG.info(ed.toString());

      eds.add(ed);

    }

    return handleExceptionInternal(ex, eds, httpHeaders, badRequest, req);

  }

}
