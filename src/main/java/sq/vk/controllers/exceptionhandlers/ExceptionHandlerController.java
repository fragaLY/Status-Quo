package sq.vk.controllers.exceptionhandlers;

import java.util.Set;
import javax.persistence.EntityNotFoundException;
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
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sq.vk.controllers.exceptionhandlers.error.ErrorDetail;
import sq.vk.core.exceptions.client.ClientNotFoundException;
import sq.vk.core.exceptions.gameinfo.GameInfoNotFound;
import sq.vk.core.exceptions.statistic.StatisticNotFoundException;

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

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest req) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);
    final HttpStatus notFound = NOT_FOUND;

    LOG.info(message);

    ErrorDetail errorDetails = new ErrorDetail.Builder()
                                 .setPropertyPath(path)
                                 .setDeveloperMessage(ex.getClass().toString())
                                 .setStatus(notFound)
                                 .setOutputMessage(message)
                               .build();

    return handleExceptionInternal(ex, errorDetails, httpHeaders, NOT_FOUND, req);

  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);

    LOG.info(message);

    ErrorDetail ed = new ErrorDetail.Builder()
                        .setPropertyPath(ex.getClass().getSimpleName().toLowerCase())
                        .setDeveloperMessage(ex.getClass().toString())
                        .setStatus(status)
                        .setOutputMessage(message)
                     .build();

    return handleExceptionInternal(ex, ed, httpHeaders, status, request);

  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);

    LOG.info(message);

    ErrorDetail ed = new ErrorDetail.Builder()
        .setPropertyPath(ex.getClass().getSimpleName())
        .setDeveloperMessage(ex.getClass().toString())
        .setStatus(status)
        .setOutputMessage(message)
        .build();

    return handleExceptionInternal(ex, ed, httpHeaders, status, request);
  }

  @Override protected ResponseEntity<Object> handleBindException(
      BindException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);

    LOG.info(message);

    ErrorDetail ed = new ErrorDetail.Builder()
        .setPropertyPath(path)
        .setDeveloperMessage(ex.getClass().toString())
        .setStatus(status)
        .setOutputMessage(message)
        .build();

    return handleExceptionInternal(ex, ed, httpHeaders, status, request);
  }

  @ExceptionHandler({StatisticNotFoundException.class, GameInfoNotFound.class, ClientNotFoundException.class, EntityNotFoundException.class})
  protected ResponseEntity<?> handleNotFoundExceptions(StatisticNotFoundException ex, WebRequest req) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);
    final HttpHeaders httpHeaders = new HttpHeaders();

    LOG.info(message);

    ErrorDetail ed = new ErrorDetail.Builder()
                       .setPropertyPath(path)
                       .setDeveloperMessage(ex.getClass().toString())
                       .setStatus(NOT_FOUND)
                       .setOutputMessage(message)
                     .build();

    return handleExceptionInternal(ex, ed, httpHeaders, NOT_FOUND, req);

  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest req) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final HttpHeaders httpHeaders = new HttpHeaders();
    final String message = env.getProperty(path);

    LOG.info(message);

    ErrorDetail ed = new ErrorDetail.Builder()
                       .setPropertyPath(path)
                       .setDeveloperMessage(ex.getClass().toString())
                       .setStatus(FORBIDDEN)
                       .setOutputMessage(message)
                     .build();

    return handleExceptionInternal(ex, ed, httpHeaders, FORBIDDEN, req);

  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<?> handleConstraintViolationException(
      ConstraintViolationException ex,
      WebRequest req) {

    final HttpHeaders httpHeaders = new HttpHeaders();
    final Set<ConstraintViolation<?>> cvs = ex.getConstraintViolations();

    Set<ErrorDetail> eds = Sets.newConcurrentHashSet();

    for (ConstraintViolation ignored : cvs) {

      final String path = ex.getClass().getSimpleName().toLowerCase();
      final String message = env.getProperty(path);

      ErrorDetail ed = new ErrorDetail.Builder()
                         .setPropertyPath(ex.getClass().getSimpleName().toLowerCase())
                         .setDeveloperMessage(ex.getClass().toString())
                         .setStatus(BAD_REQUEST)
                         .setOutputMessage(message)
                       .build();

      LOG.info("ConstraintViolation error: {}", ed.toString());

      eds.add(ed);

    }

    return handleExceptionInternal(ex, eds, httpHeaders, BAD_REQUEST, req);

  }

}
