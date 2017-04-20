package sq.vk.controllers.exceptionhandlers;

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
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sq.vk.controllers.exceptionhandlers.errordetails.ErrorDetails;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
@ControllerAdvice
@PropertySources(@PropertySource("classpath:i18n/validation_errors_en.properties"))
public class CommonExceptionsHandler extends ResponseEntityExceptionHandler{

  private static final Logger LOG = LoggerFactory.getLogger(CommonExceptionsHandler.class);

  @Autowired
  private Environment env;

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest req) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);

    ErrorDetails ed = new ErrorDetails.Builder()
                        .setPropertyPath(path)
                        .setDeveloperMessage(ex.getClass().toString())
                        .setStatus(status)
                        .setOutputMessage(message)
                      .build();

    LOG.info(ed.toString());

    return handleExceptionInternal(ex, ed, httpHeaders, status, req);

  }

  @ExceptionHandler(value = Exception.class)
  protected ResponseEntity<ErrorDetails> handleAllException(Exception ex) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);
    final HttpStatus status = HttpStatus.BAD_REQUEST;

    ErrorDetails ed = new ErrorDetails.Builder()
                        .setPropertyPath(path)
                        .setDeveloperMessage(ex.getClass().toString())
                        .setStatus(status)
                        .setOutputMessage(message)
                      .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, httpHeaders, status);

  }

}
