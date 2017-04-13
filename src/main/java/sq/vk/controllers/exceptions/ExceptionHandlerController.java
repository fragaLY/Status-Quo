package sq.vk.controllers.exceptions;

import javax.persistence.EntityNotFoundException;

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
import sq.vk.statistic.exceptions.RoomNotFoundException;
import sq.vk.statistic.exceptions.StatisticNotFoundException;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
  private static final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;

  @ExceptionHandler(StatisticNotFoundException.class)
  protected ResponseEntity<Object> handleStatisticNotFoundExceptionHandler(
    StatisticNotFoundException ex,
    WebRequest request) {
    final String responseBody = "Something went wrong with info of statistic";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, request);
  }

  @ExceptionHandler(ClientNotFoundException.class)
  protected ResponseEntity<Object> handleClientNotFoundExceptionHandler(
    ClientNotFoundException ex,
    WebRequest request) {
    final String responseBody = "Something went wrong with client's info";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, request);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    final String responseBody = "You do not have sufficient rights to view this page";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, FORBIDDEN, request);
  }

  @ExceptionHandler(RoomNotFoundException.class)
  protected ResponseEntity<Object> handleClientNotFoundExceptionHandler(
      RoomNotFoundException ex,
      WebRequest request) {
    final String responseBody = "Something went wrong with the info of room";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, request);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    final String responseBody = "There is no current page";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return super.handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFoundExceptionHandler(
      EntityNotFoundException ex,
      WebRequest request) {
    final String responseBody = "Information was not found";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, NOT_FOUND, request);
  }

}
