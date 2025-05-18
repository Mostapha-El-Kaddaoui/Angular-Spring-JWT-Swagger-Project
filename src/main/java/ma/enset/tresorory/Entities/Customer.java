package ma.enset.tresorory.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer")
    //PENDANT L SRIALIZATION , JE DEMANDE DE PAS LIRE CETTE LIST
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;
}
