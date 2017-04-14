package sq.vk.statistic.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sq.vk.statistic.domain.PokerRoomType;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public class StatisticDto {

    private Integer id;

    @Pattern(regexp = "[A-Za-z0-9_+-.,!@#$%^&*();|<>\"':?=]+")
    @Size(min = 1, max = 20)
    private String name;

    private PokerRoomType pokerRoom;

    @Pattern(regexp = "(?:0)|([1-9][0-9]*)\\.?[0-9]+")
    private double profit;

    private StatisticDto(Builder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.pokerRoom = builder.pokerRoom;
        this.profit = builder.profit;

    }

    public StatisticDto(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokerRoomType getPokerRoom() {
        return pokerRoom;
    }

    public void setPokerRoom(PokerRoomType pokerRoom) {
        this.pokerRoom = pokerRoom;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StatisticDto that = (StatisticDto) o;

        return new EqualsBuilder()
                .append(profit, that.profit)
                .append(id, that.id)
                .append(name, that.name)
                .append(pokerRoom, that.pokerRoom)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(pokerRoom)
                .append(profit)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "StatisticDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pokerRoom=" + pokerRoom +
                ", profit=" + profit +
                '}';
    }

    public static class Builder {
        private Integer id;
        private String name;
        private PokerRoomType pokerRoom;
        private double profit;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPokerRoom(PokerRoomType pokerRoom) {
            this.pokerRoom = pokerRoom;
            return this;
        }

        public Builder setProfit(double profit) {
            this.profit = profit;
            return this;
        }

        public StatisticDto build() {
            return new StatisticDto(this);
        }

    }
}
