package sq.vk.core.domain.statistic;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sq.vk.core.domain.client.Client;
import sq.vk.core.domain.gameinfo.GameInfo;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 *
 * Statistic repository.
 */
@Entity
@Table(name = "statistics")
public class Statistic {

  private Integer id;
  private String name;
  private double profit;
  private GameInfo gameInfo;
  private Client client;
  private LocalDate from;
  private LocalDate to;

  public Statistic() {
  }

  private Statistic(Builder builder) {

    this.id = builder.id;
    this.name = builder.name;
    this.profit = builder.profit;
    this.gameInfo = builder.gameInfo;
    this.client = builder.client;
    this.from = builder.from;
    this.to = builder.to;

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

  @Column(name = "player_name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "profit")
  public double getProfit() {
    return profit;
  }

  public void setProfit(double profit) {
    this.profit = profit;
  }

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(name = "statistic_gameinfo",
             joinColumns = { @JoinColumn(name = "statistic_id", nullable = false, updatable = false) },
             inverseJoinColumns = { @JoinColumn(name = "gameinfo_id", nullable = false) })
  public GameInfo getGameInfo() {
    return gameInfo;
  }

  public void setGameInfo(GameInfo gameInfo) {
    this.gameInfo = gameInfo;
  }

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "statistic_client",
             joinColumns = { @JoinColumn(name = "statistic_id", nullable = false, updatable = false) },
             inverseJoinColumns = { @JoinColumn(name = "client_id", nullable = false) })
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Column(name = "date_from", columnDefinition = "DATE")
  public LocalDate getFrom() {
    return from;
  }

  public void setFrom(LocalDate from) {
    this.from = from;
  }

  @Column(name = "date_to", columnDefinition = "DATE")
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

    Statistic statistic = (Statistic)o;

    return new EqualsBuilder()
             .append(profit, statistic.profit)
             .append(id, statistic.id)
             .append(name,statistic.name)
             .append(from,statistic.from)
             .append(to, statistic.to)
           .isEquals();

  }

  @Override public int hashCode() {

    return new HashCodeBuilder(17,37)
            .append(id)
            .append(name)
            .append(profit)
            .append(from)
            .append(to)
          .toHashCode();

  }

  @Override public String toString() {

    return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("profit", profit)
            .append("from", from)
            .append("to", to)
          .toString();

  }

  public static class Builder {

    private Integer id;
    private String name;
    private double profit;
    private GameInfo gameInfo;
    private Client client;
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

    public Builder setGameInfo(GameInfo gameInfo) {
      this.gameInfo = gameInfo;
      return this;
    }

    public Builder setClient(Client client) {
      this.client = client;
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

    public Statistic build() {
      return new Statistic(this);
    }

  }

}
