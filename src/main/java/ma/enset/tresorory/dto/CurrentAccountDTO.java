package ma.enset.tresorory.dto;

import lombok.Data;
import ma.enset.tresorory.Enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
