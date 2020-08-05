package com.example.ewallet.dto;

import com.example.ewallet.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransactionDTO {
  @NotNull private String type;

  @NotNull private String iban;

  @NotNull private String description;

  @NotNull private Double sum;

  @NotNull private LocalDateTime dateTime;

  @NotNull private String name;

  @NotNull private Long cnp;

  public TransactionDTO fromEntity(Transaction transaction) {
    return TransactionDTO.builder()
        .type(transaction.getType().name())
        .iban(transaction.getIban())
        .name(transaction.getName())
        .description(transaction.getDescription())
        .sum(transaction.getSum())
        .dateTime(transaction.getDateTime())
        .cnp(transaction.getUser().getCnp())
        .build();
  }
}
