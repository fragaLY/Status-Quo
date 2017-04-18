package sq.vk.gameinfo.dto;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sq.vk.gameinfo.domain.PokerRoomType;
import sq.vk.gameinfo.domain.Speed;
import sq.vk.gameinfo.domain.TableSize;
import sq.vk.gameinfo.domain.Type;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
public class GameInfoDto {

  private Integer id;

  @Size(min = 3, max = 20)
  private PokerRoomType roomType;

  @Size(min = 3, max = 20)
  private Speed speed;

  @Size(min = 3, max = 20)
  private TableSize size;

  @Size(min = 3, max = 20)
  private Type type;

  public GameInfoDto(){
  }

  public GameInfoDto(Builder builder){

    this.id = builder.id;
    this.roomType = builder.roomType;
    this.speed = builder.speed;
    this.size = builder.size;
    this.type = builder.type;

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PokerRoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(PokerRoomType roomType) {
    this.roomType = roomType;
  }

  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
  }

  public TableSize getSize() {
    return size;
  }

  public void setSize(TableSize size) {
    this.size = size;
  }

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

    GameInfoDto gameInfo = (GameInfoDto)o;

    return new EqualsBuilder()
             .append(id,gameInfo.id)
             .append(roomType, gameInfo.roomType)
             .append(speed, gameInfo.speed)
             .append(size,gameInfo.size)
             .append(type, gameInfo.type)
           .isEquals();

  }

  @Override
  public int hashCode() {

    return new HashCodeBuilder(17,37)
             .append(id)
             .append(roomType)
             .append(speed)
             .append(size)
             .append(type)
           .toHashCode();

  }

  @Override public String toString() {

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

    public GameInfoDto build(){
      return new GameInfoDto(this);
    }

  }

}
