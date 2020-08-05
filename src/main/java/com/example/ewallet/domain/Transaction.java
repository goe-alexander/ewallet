package com.example.ewallet.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id @GeneratedValue private Long id;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionType type;

  @Column @NotNull private String iban;

  @Column @NotNull private String name;

  @Column @NotNull private String description;

  @Column
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dateTime;

  @Column @NotNull private Double sum;

  @JsonIgnore
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Override
  public String toString() {
    return "Transaction{"
        + "id="
        + id
        + ", type="
        + type
        + ", iban='"
        + iban
        + '\''
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", dateTime="
        + dateTime
        + ", sum="
        + sum
        + '}';
  }
}
