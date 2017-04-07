package sq.vk.dto.item;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public class ItemDto {

  private String id;
  private String name;
  private String size;
  private double price;

  private ItemDto(Builder builder){

    this.id = builder.id;
    this.name = builder.name;
    this.size = builder.size;
    this.price = builder.price;

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ItemDto itemDto = (ItemDto)o;

    return new EqualsBuilder().append(price, itemDto.price).append(id, itemDto.id).append(
        name,
        itemDto.name).append(size, itemDto.size).isEquals();
  }

  @Override public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(name).append(size).append(price).toHashCode();
  }

  @Override public String toString() {
    return new ToStringBuilder(this).append("id", id).append("name", name).append(
        "size",
        size).append("price", price).toString();
  }

  public static class Builder {
    private String id;
    private String name;
    private String size;
    private double price;

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setSize(String size) {
      this.size = size;
      return this;
    }

    public Builder setPrice(double price) {
      this.price = price;
      return this;
    }

    public ItemDto build() {
      return new ItemDto(this);
    }

  }}
