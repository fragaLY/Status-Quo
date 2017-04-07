package sq.vk.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Entity
@Table(name = "items")
public class Item {

  private String id;
  private String name;
  private String size;
  private double price;
  private int isAvailable;

  public Item() {
  }

  private Item(Builder builder) {

    this.id = builder.id;
    this.name = builder.name;
    this.size = builder.size;
    this.price = builder.price;

  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "name")
  @NotBlank(message = "Name should not be blank")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "size")
  @NotBlank(message = "Size should not be blank")
  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  @Column(name = "price")
  @NotBlank(message = "Price should not be blank")
  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Column(name = "available")
  public int isAvailable() {
    return isAvailable;
  }

  public void setAvailable(Integer available) {
    isAvailable = available;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    Item item = (Item)o;

    return new EqualsBuilder().append(price, item.price).append(id, item.id).append(name, item.name).append(
      size,
      item.size).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(name).append(size).append(price).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", id).append("name", name).append("size", size).append(
      "price",
      price).toString();
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

    public Item build() {
      return new Item(this);
    }

  }

}
