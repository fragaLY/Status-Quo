package sq.vk.controllers.exceptions.error;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

/**
 * Created by Vadzim_Kavalkou on 4/14/2017.
 */
public class ErrorDetail {

  private String developerMessage;
  private HttpStatus status;
  private String propertyPath;
  private String outputMessage;

  public ErrorDetail() {
  }

  public ErrorDetail(Builder builder) {

    this.developerMessage = builder.developerMessage;
    this.status = builder.status;
    this.propertyPath = builder.propertyPath;
    this.outputMessage = builder.outputMessage;
  }

  public String getOutputMessage() {
    return outputMessage;
  }

  public void setOutputMessage(String outputMessage) {
    this.outputMessage = outputMessage;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }

  public String getPropertyPath() {
    return propertyPath;
  }

  public void setPropertyPath(String propertyPath) {
    this.propertyPath = propertyPath;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("developerMessage", developerMessage).append(
      "status",
      status).append("propertyPath", propertyPath).append("outputMessage", outputMessage).toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ErrorDetail that = (ErrorDetail)o;

    return new EqualsBuilder().append(status, that.status).append(propertyPath, that.propertyPath).append(
      outputMessage,
      that.outputMessage).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(status).append(propertyPath).append(outputMessage).toHashCode();
  }

  public static final class Builder {

    private String developerMessage;
    private HttpStatus status;
    private String propertyPath;
    private String outputMessage;

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

    public ErrorDetail build() {
      return new ErrorDetail(this);
    }

  }
}
