package sq.vk.controllers.exceptions;

import java.util.Set;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sq.vk.client.exceptions.ClientNotFoundException;
import sq.vk.controllers.exceptions.error.ErrorDetail;
import sq.vk.statistic.exceptions.RoomNotFoundException;
import sq.vk.statistic.exceptions.StatisticNotFoundException;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
@ControllerAdvice
@PropertySources({
    @PropertySource("classpath:i18n/validation_errors_en.properties"),
    @PropertySource("classpath:i18n/validation_errors_ru.properties") })
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);

  private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
  private static final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;
  private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

  @Autowired
  private Environment env;

  @ExceptionHandler(StatisticNotFoundException.class)
  protected ResponseEntity<?> handleStatisticNotFoundException(
    StatisticNotFoundException ex,
    WebRequest req) {

    final String responseBody = "Statistic was not found.";

    LOG.info(responseBody);

    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, req);
  }

  @ExceptionHandler(ClientNotFoundException.class)
  protected ResponseEntity<?> handleClientNotFoundException(ClientNotFoundException ex, WebRequest req) {

    final String responseBody = "Client was not found.";

    LOG.info(responseBody);

    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, req);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest req) {

    final String responseBody = "Access denied.";

    LOG.info(responseBody);

    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, FORBIDDEN, req);
  }

  @ExceptionHandler(RoomNotFoundException.class)
  protected ResponseEntity<Object> handleClientNotFoundException(RoomNotFoundException ex, WebRequest req) {

    final String responseBody = "Room was not found.";

    LOG.info(responseBody);

    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, req);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
    NoHandlerFoundException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest req) {

    final String responseBody = "Page was not found.";

    LOG.info(responseBody);

    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, req);

  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest req) {

    final String responseBody = "Entity was not found";

    LOG.info(responseBody);

    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, req);

  }

  @ExceptionHandler({ConstraintViolationException.class, RollbackException.class})
  protected ResponseEntity<Object> handleEntityNotFoundException(
    ConstraintViolationException ex,
    WebRequest req) {


    final HttpHeaders httpHeaders = new HttpHeaders();

    final Set<ConstraintViolation<?>> cvs = ex.getConstraintViolations();

    Set<ErrorDetail> errorDetails = Sets.newConcurrentHashSet();

    for (ConstraintViolation cv : cvs) {

      final String template = cv.getMessageTemplate();
      final String path = cv.getPropertyPath().toString();
      final String output = env.getProperty(path);

      ErrorDetail ed = new ErrorDetail.Builder()
          .setPropertyPath(path)
          .setDeveloperMessage(template)
          .setStatus(BAD_REQUEST)
          .setOutputMessage(output)
          .build();

      LOG.info("ConstraintViolation error: {}", ed.toString());

      errorDetails.add(ed);

    }

    return handleExceptionInternal(ex, errorDetails, httpHeaders, BAD_REQUEST, req);

  }

}
