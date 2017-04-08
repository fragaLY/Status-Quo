package sq.vk.domain.statistic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Entity
@Table(name = "statistics")
public class Statistic {

    private Integer id;
    private String name;
    private PokerRoomType pokerRoom;
    private double profit;

    public Statistic() {
    }

    private Statistic(Builder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.pokerRoom = builder.pokerRoom;
        this.profit = builder.profit;

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

    @Column(name = "name")
    @NotBlank(message = "Name should not be blank")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.ORDINAL)
    public PokerRoomType getPokerRoom() {
        return pokerRoom;
    }

    public void setPokerRoom(PokerRoomType pokerRoom) {
        this.pokerRoom = pokerRoom;
    }

    @Column(name = "profit")
    @NotBlank(message = "Profit should not be blank")
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

        Statistic statistic = (Statistic) o;

        return new EqualsBuilder()
                .append(profit, statistic.profit)
                .append(id, statistic.id)
                .append(name, statistic.name)
                .append(pokerRoom, statistic.pokerRoom)
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
        return "Statistic{" +
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

        public Statistic build() {
            return new Statistic(this);
        }

    }

}
