package ma.enset.tresorory.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
//La strategy Single Table
@DiscriminatorValue("SAV")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SavingAccount extends BankAccount{
    private double duration;
}
