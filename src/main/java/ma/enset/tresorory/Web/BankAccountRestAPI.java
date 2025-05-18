package ma.enset.tresorory.Web;

import ma.enset.tresorory.Exceptions.CustomerNotFoundException;
import ma.enset.tresorory.Service.BankAccountService;
import ma.enset.tresorory.dto.*;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@CrossOrigin("*")

public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "id") String accountId){
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }

    @PostMapping("/accounts")
    public BankAccountDTO saveAccount(@RequestBody BankAccountDTO bankAccountDTO) throws CustomerNotFoundException {
        BankAccountDTO savedBankAccount;
        if (bankAccountDTO instanceof SavingAccountDTO){
            savedBankAccount = bankAccountService.saveSavingBankAccount(((SavingAccountDTO) bankAccountDTO).getBalance(), ((SavingAccountDTO) bankAccountDTO).getDuration(), ((SavingAccountDTO) bankAccountDTO).getCustomerDTO().getId());
        }else{
            savedBankAccount = bankAccountService.saveCurrentBankAccount(((CurrentAccountDTO) bankAccountDTO).getBalance(), ((CurrentAccountDTO) bankAccountDTO).getOverDraft(), ((CurrentAccountDTO) bankAccountDTO).getCustomerDTO().getId());
        }
        return savedBankAccount;
    }

//    @GetMapping("/accounts/{accountId}/operations")
//    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
//        return bankAccountService.getAccountHistory(accountId);
//    }
    @GetMapping("/accounts/{accountId}/pageoperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size){
        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
