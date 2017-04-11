package sq.vk.controllers.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sq.vk.exceptions.client.ClientNotFoundException;
import sq.vk.exceptions.statistic.RoomNotFoundException;
import sq.vk.exceptions.statistic.StatisticNotFoundException;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(StatisticNotFoundException.class)
  protected ResponseEntity<Object> handleStatisticNotFoundExceptionHandler(
    StatisticNotFoundException ex,
    WebRequest request) {
    final String responseBody = "Something went wrong with info of statistic";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(ClientNotFoundException.class)
  protected ResponseEntity<Object> handleClientNotFoundExceptionHandler(
    ClientNotFoundException ex,
    WebRequest request) {
    final String responseBody = "Something went wrong with client's info";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<Object> hanldeAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    final String responseBody = "You do not have sufficient rights to view this page";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, HttpStatus.FORBIDDEN, request);
  }

  @ExceptionHandler(RoomNotFoundException.class)
  protected ResponseEntity<Object> handleClientNotFoundExceptionHandler(
      RoomNotFoundException ex,
      WebRequest request) {
    final String responseBody = "Something went wrong with the info of room";
    final HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, responseBody, httpHeaders, HttpStatus.NOT_FOUND, request);
  }

}
