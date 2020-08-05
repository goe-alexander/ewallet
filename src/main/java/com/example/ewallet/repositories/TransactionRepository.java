package com.example.ewallet.repositories;

import com.example.ewallet.domain.Transaction;
import com.example.ewallet.interfaces.TransactionReportProj;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
  @Query(
      value =
          "SELECT "
              + "   u.cnp as cnp, "
              + "   t.iban as iban, "
              + "   t.sum as sum, "
              + "   t.type as type "
              + " FROM"
              + "   User u "
              + " INNER JOIN "
              + "  u.transactions as t")
  @ReadOnlyProperty
  List<TransactionReportProj> getAllTransactionProjections();

  @Query(
      value =
          "SELECT "
              + "   u.cnp as cnp, "
              + "   t.iban as iban, "
              + "   t.sum as sum, "
              + "   t.type as type "
              + " FROM"
              + "   User u "
              + " INNER JOIN "
              + "  u.transactions as t"
              + " WHERE "
              + " u.cnp = :cnp ")
  List<TransactionReportProj> getReportProjectionByCnp(@Param("cnp") Long cnp);
}
