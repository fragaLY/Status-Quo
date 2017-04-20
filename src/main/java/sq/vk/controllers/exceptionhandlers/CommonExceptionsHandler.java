package sq.vk.controllers.exceptionhandlers;

import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sq.vk.controllers.exceptionhandlers.errordetails.ErrorDetails;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
@ControllerAdvice
@PropertySources(@PropertySource("classpath:i18n/validation_errors_en.properties"))
public class CommonExceptionsHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(CommonExceptionsHandler.class);

  @Autowired
  private Environment env;

  @Autowired
  private MessageSource messageSource;

  //404
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
    NoHandlerFoundException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest req) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String message = env.getProperty(path);

    ErrorDetails ed = new ErrorDetails.Builder()
                        .setPropertyPath(path)
                        .setDeveloperMessage(ex.getClass().toString())
                        .setStatus(status)
                        .setOutputMessage(message)
                      .build();

    LOG.info(ed.toString());

    return handleExceptionInternal(ex, ed, headers, status, req);

  }

  // 400
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    final MethodArgumentNotValidException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final Set<String> errors = Sets.newConcurrentHashSet();
    final String path = ex.getClass().getSimpleName().toLowerCase();

    ex.getBindingResult().getFieldErrors().stream()
        .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

    ex.getBindingResult().getGlobalErrors().stream()
        .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return handleExceptionInternal(ex, ed, headers, ed.getStatus(), request);

  }

  @Override
  protected ResponseEntity<Object> handleBindException(
    final BindException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final Set<String> errors = Sets.newConcurrentHashSet();
    final String path = ex.getClass().getSimpleName().toLowerCase();

    ex.getBindingResult().getFieldErrors().stream()
        .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));

    ex.getBindingResult().getGlobalErrors().stream()
        .forEach(error -> errors.add(error.getObjectName() + ": " + error.getDefaultMessage()));

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return handleExceptionInternal(ex, ed, headers, ed.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
    final TypeMismatchException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String error = new StringBuilder(ex.getValue().toString())
                           .append(" value for ")
                           .append(ex.getPropertyName())
                           .append(" should be of type ")
                           .append(ex.getRequiredType())
                         .toString();

    final Set<String> errors = Sets.newHashSet(error);

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
    final MissingServletRequestPartException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String error = ex.getRequestPartName() + " part is missing";
    final Set<String> errors = Sets.newHashSet(error);

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    final MissingServletRequestParameterException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String error = ex.getParameterName() + " parameter is missing";
    final Set<String> errors = Sets.newHashSet(error);

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
    final MethodArgumentTypeMismatchException ex,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
    final Set<String> errors = Sets.newHashSet(error);

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleConstraintViolation(
    final ConstraintViolationException ex,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final Set<String> errors = ex.getConstraintViolations().stream()
        .map(cv -> new StringBuilder()
            .append(cv.getRootBeanClass().getName())
            .append(" ")
            .append(cv.getPropertyPath())
            .append(": ")
            .append(cv.getMessage()).toString())
        .collect(Collectors.toCollection(Sets::newConcurrentHashSet));

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.BAD_REQUEST)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  //405
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
    final HttpRequestMethodNotSupportedException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final StringBuilder builder = new StringBuilder()
                                  .append(ex.getMethod())
                                  .append(" method is not supported for this request. Supported methods are ");

    ex.getSupportedHttpMethods().forEach(method -> builder.append(method).append(" "));

    final Set<String> errors = Sets.newHashSet(builder.toString());

    final ErrorDetails ed = new ErrorDetails.Builder()
                                  .setPropertyPath(path)
                                  .setDeveloperMessage(ex.getClass().toString())
                                  .setStatus(HttpStatus.METHOD_NOT_ALLOWED)
                                  .setOutputMessage(ex.getLocalizedMessage())
                                  .setErrors(errors)
                                .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  //415
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
    final HttpMediaTypeNotSupportedException ex,
    final HttpHeaders headers,
    final HttpStatus status,
    final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final StringBuilder builder = new StringBuilder()
                                  .append(ex.getContentType())
                                  .append(" media type is not supported. Supported media types are ");

    ex.getSupportedMediaTypes().forEach(type -> builder.append(type).append(" "));

    final Set<String> errors = Sets.newHashSet(builder.substring(0, builder.length() - 2));

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

  //500
  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {

    final String path = ex.getClass().getSimpleName().toLowerCase();
    final Set<String> errors = Sets.newHashSet();

    final ErrorDetails ed = new ErrorDetails.Builder()
                              .setPropertyPath(path)
                              .setDeveloperMessage(ex.getClass().toString())
                              .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                              .setOutputMessage(ex.getLocalizedMessage())
                              .setErrors(errors)
                            .build();

    LOG.info(ed.toString());
    LOG.error(ex.getLocalizedMessage());

    return new ResponseEntity<>(ed, new HttpHeaders(), ed.getStatus());
  }

}
