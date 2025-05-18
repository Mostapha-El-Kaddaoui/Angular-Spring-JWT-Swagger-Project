package ma.enset.tresorory.Service;

import ma.enset.tresorory.Exceptions.CustomerNotFoundException;
import ma.enset.tresorory.dto.*;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> getAccountHistory(String accountId);

    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustomers();

    BankAccountDTO getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);

    SavingAccountDTO saveSavingBankAccount(double initialBalance, double duration, Long customerId) throws CustomerNotFoundException;

    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size);

    List<CustomerDTO> searchCustomers(String keyword);


}
