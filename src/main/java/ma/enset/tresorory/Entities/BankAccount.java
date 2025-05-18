package ma.enset.tresorory.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.tresorory.Enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Entity
//La strategy Single Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name = "TYPE", length = 4)
//La strategy Table Per Classe
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//La strategy join
//@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
//on ajoute abstract pour pas creer une nouvelle table pour BankAccount
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    // cette anotation permet d'enregistrer status 0, 1,et 2, @Enumerated(EnumType.ORDINAL)
    // et celle ci permet d'enregistrer status en forme string CREATED, ACTIVATED, SUSPENDED @Enumerated(EnumType.STRING)

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    // fetch = FetchType.LAZY ==> retourne de la base de donnees seulement les attribute id, balance, createdAt, status, customer, et les relations One To Many sont charger selement a la demande
    // fetch = FetchType.EAGER ==> retourne en plus de LAZY Les relations one to many
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    private List<AccountOperation> accountOperations;
}
