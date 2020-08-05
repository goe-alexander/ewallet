package com.example.ewallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportDTO {
  private Long cnp;
  private String iban;
  private List<TransactionTypeReportDTO> typeDetails;
}
