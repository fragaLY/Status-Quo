package sq.vk.core.domain.gameinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 *
 * GameInfo repository.
 */
@Entity
@Table(name = "gameInfo")
public class GameInfo {

  private Integer id;
  private PokerRoomType roomType;
  private Speed speed;
  private TableSize size;
  private Type type;

  public GameInfo() {
  }

  private GameInfo(Builder builder) {

    this.id = builder.id;
    this.roomType = builder.roomType;
    this.speed = builder.speed;
    this.size = builder.size;
    this.type = builder.type;

  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "pokerroom", nullable = false)
  public PokerRoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(PokerRoomType roomType) {
    this.roomType = roomType;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "speed", nullable = false)
  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "size", nullable = false)
  public TableSize getSize() {
    return size;
  }

  public void setSize(TableSize size) {
    this.size = size;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    GameInfo gameInfo = (GameInfo)o;

    return new EqualsBuilder()
            .append(id, gameInfo.id)
            .append(roomType, gameInfo.roomType)
            .append(speed,gameInfo.speed)
            .append(size, gameInfo.size)
            .append(type, gameInfo.type)
          .isEquals();

  }

  @Override
  public int hashCode() {

    return new HashCodeBuilder(17, 37)
            .append(id)
            .append(roomType)
            .append(speed)
            .append(size)
            .append(type)
          .toHashCode();

  }

  @Override
  public String toString() {

    return new ToStringBuilder(this)
            .append("id", id)
            .append("roomType", roomType)
            .append("speed",speed)
            .append("size", size)
            .append("type", type)
          .toString();

  }

  public static class Builder {

    private Integer id;
    private PokerRoomType roomType;
    private Speed speed;
    private TableSize size;
    private Type type;

    public Builder setRoomType(PokerRoomType roomType) {
      this.roomType = roomType;
      return this;
    }

    public Builder setSpeed(Speed speed) {
      this.speed = speed;
      return this;
    }

    public Builder setSize(TableSize size) {
      this.size = size;
      return this;
    }

    public Builder setType(Type type) {
      this.type = type;
      return this;
    }

    public Builder setId(Integer id) {
      this.id = id;
      return this;
    }

    public GameInfo build() {
      return new GameInfo(this);
    }

  }

}
