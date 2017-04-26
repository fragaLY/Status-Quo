package sq.vk.controllers.exceptionhandlers.errordetails;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

/**
 * Created by Vadzim_Kavalkou on 4/14/2017.
 */
public class ErrorDetails {

  @JsonIgnore
  private String developerMessage;

  @JsonIgnore
  private String propertyPath;

  private HttpStatus status;
  private String outputMessage;
  private Set<String> errors;

  public ErrorDetails() {
  }

  private ErrorDetails(Builder builder) {

    this.developerMessage = builder.developerMessage;
    this.status = builder.status;
    this.propertyPath = builder.propertyPath;
    this.outputMessage = builder.outputMessage;
    this.errors = builder.errors;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this)
            .append("developerMessage", developerMessage)
            .append("status", status)
            .append("propertyPath", propertyPath)
            .append("outputMessage", outputMessage)
            .append("errors", errors)
          .toString();
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ErrorDetails that = (ErrorDetails)o;

    return new EqualsBuilder()
            .append(status, that.status)
            .append(propertyPath, that.propertyPath)
            .append(outputMessage, that.outputMessage)
            .append(errors, that.errors)
          .isEquals();
  }

  @Override
  public int hashCode() {

    return new HashCodeBuilder(17, 37)
            .append(status)
            .append(propertyPath)
            .append(outputMessage)
            .append(errors)
          .toHashCode();

  }

  public static final class Builder {

    private String developerMessage;
    private HttpStatus status;
    private String propertyPath;
    private String outputMessage;

    private Set<String> errors;

    public Builder setOutputMessage(String outputMessage) {
      this.outputMessage = outputMessage;
      return this;
    }

    public Builder setDeveloperMessage(String developerMessage) {
      this.developerMessage = developerMessage;
      return this;
    }

    public Builder setStatus(HttpStatus status) {
      this.status = status;
      return this;
    }

    public Builder setPropertyPath(String propertyPath) {
      this.propertyPath = propertyPath;
      return this;
    }

    public Builder setErrors(Set<String> errors) {
      this.errors = errors;
      return this;
    }

    public ErrorDetails build() {
      return new ErrorDetails(this);
    }

  }

}
