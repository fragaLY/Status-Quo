package sq.vk.playerstatistic.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sq.vk.statistic.domain.PokerRoomType;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
public class GameInfo {

  private PokerRoomType roomType;
  private Speed speed;
  private TableSize size;
  private Type type;
  private String name;
  private double startOfBuyInRange;
  private double endOfBuyInRange;

  public GameInfo() {
  }

  public GameInfo(Builder builder){

    this.roomType = builder.roomType;
    this.speed = builder.speed;
    this.size = builder.size;
    this.type = builder.type;
    this.name = builder.name;
    this.startOfBuyInRange = builder.startOfBuyInRange;
    this.endOfBuyInRange = builder.endOfBuyInRange;

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

  public double getStartOfBuyInRange() {
    return startOfBuyInRange;
  }

  public void setStartOfBuyInRange(double startOfBuyInRange) {
    this.startOfBuyInRange = startOfBuyInRange;
  }

  public double getEndOfBuyInRange() {
    return endOfBuyInRange;
  }

  public void setEndOfBuyInRange(double endOfBuyInRange) {
    this.endOfBuyInRange = endOfBuyInRange;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    GameInfo gameInfo = (GameInfo)o;

    return new EqualsBuilder().append(startOfBuyInRange, gameInfo.startOfBuyInRange).append(
      endOfBuyInRange,
      gameInfo.endOfBuyInRange).append(roomType, gameInfo.roomType).append(speed, gameInfo.speed).append(
        size,
        gameInfo.size).append(type, gameInfo.type).append(name, gameInfo.name).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(roomType).append(speed).append(size).append(type).append(
      startOfBuyInRange).append(endOfBuyInRange).append(name).toHashCode();
  }

  @Override
  public String toString() {

    return new ToStringBuilder(this).append("roomType", roomType).append("speed", speed).append(
      "size",
      size).append("startOfBuyInRange", startOfBuyInRange).append("endOfBuyInRange", endOfBuyInRange).append(
        "type",
        type).append("name", name).toString();

  }

  public static class Builder {

    private PokerRoomType roomType;
    private Speed speed;
    private TableSize size;
    private Type type;
    private String name;
    private double startOfBuyInRange;
    private double endOfBuyInRange;

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

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setStartOfBuyInRange(double startOfBuyInRange) {
      this.startOfBuyInRange = startOfBuyInRange;
      return this;
    }

    public Builder setEndOfBuyInRange(double endOfBuyInRange) {
      this.endOfBuyInRange = endOfBuyInRange;
      return this;
    }

    public GameInfo build(){
      return new GameInfo(this);
    }

  }

}
