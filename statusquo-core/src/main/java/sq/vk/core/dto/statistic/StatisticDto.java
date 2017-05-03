package sq.vk.core.dto.statistic;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.hateoas.ResourceSupport;
import sq.vk.core.dto.gameinfo.GameInfoDto;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 *
 * StatisticDto.class
 */
public class StatisticDto extends ResourceSupport {

    private Integer id;

    @Pattern(regexp = "[A-Za-z0-9_+-.,!@#$%^&*();|<>\"':?=]+")
    @Size(min = 1, max = 20)
    private String name;

    @Pattern(regexp = "(-?0\\.[0-9]*[1-9]+[0-9]*)|(-?[1-9]+[0-9]*((\\.[0-9]*[1-9]+[0-9]*)|(\\.[0-9]+)))|(-?[1-9]+[0-9]*)|(0)")
    private double profit;

    @NotNull
    private GameInfoDto gameInfo;

    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;

    public StatisticDto() {
    }

    private StatisticDto(Builder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.profit = builder.profit;
        this.gameInfo = builder.gameInfo;
        this.from = builder.from;
        this.to = builder.to;

    }

    public Integer getStatisticId() {
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

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public GameInfoDto getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfoDto gameInfo) {
        this.gameInfo = gameInfo;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        StatisticDto that = (StatisticDto)o;

        return new EqualsBuilder()
                   .append(profit, that.profit)
                   .append(id, that.id)
                   .append(name, that.name)
                   .append(gameInfo, that.gameInfo)
                   .append(from, that.from)
                   .append(to, that.to)
               .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17,37)
                   .append(id)
                   .append(name)
                   .append(profit)
                   .append(gameInfo)
                   .append(from)
                   .append(to)
               .toHashCode();
    }

    @Override public String toString() {
        return new ToStringBuilder(this)
                   .append("id", id)
                   .append("name", name)
                   .append("profit",profit)
                   .append("gameInfo", gameInfo)
                   .append("from", from)
                   .append("to", to)
               .toString();
    }

    public static class Builder {

        private Integer id;
        private String name;
        private double profit;
        private GameInfoDto gameInfo;
        private LocalDate from;
        private LocalDate to;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setProfit(double profit) {
            this.profit = profit;
            return this;
        }

        public Builder setGameInfo(GameInfoDto gameInfo) {
            this.gameInfo = gameInfo;
            return this;
        }

        public Builder setFrom(LocalDate from) {
            this.from = from;
            return this;
        }

        public Builder setTo(LocalDate to) {
            this.to = to;
            return this;
        }

        public StatisticDto build() {
            return new StatisticDto(this);
        }

    }

}
