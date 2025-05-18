package ma.enset.tresorory.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.tresorory.Entities.*;
import ma.enset.tresorory.Enums.OperationType;
import ma.enset.tresorory.Exceptions.BalanceNotSufficentException;
import ma.enset.tresorory.Exceptions.BankAccountNotFoundException;
import ma.enset.tresorory.Exceptions.CustomerNotFoundException;
import ma.enset.tresorory.Mappers.BankAccountMapperImpl;
import ma.enset.tresorory.Repositories.AccountOperationRepository;
import ma.enset.tresorory.Repositories.BankAccountRepository;
import ma.enset.tresorory.Repositories.CustomerRepository;
import ma.enset.tresorory.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private final CustomerRepository customerRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final BankAccountMapperImpl bankAccountMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> getAccountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map((bankAccount->bankAccountMapper.fromAccountOperation(bankAccount))).collect(Collectors.toList());
    };
    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount bankCurrentAccount = new CurrentAccount();
        bankCurrentAccount.setId(UUID.randomUUID().toString());
        bankCurrentAccount.setCreatedAt(new Date());
        bankCurrentAccount.setOverDraft(overDraft);
        bankCurrentAccount.setBalance(initialBalance);
        bankCurrentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(bankCurrentAccount);
        return bankAccountMapper.fromCurrentAccount(savedBankAccount);
    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double duration, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount bankSavingAccount = new SavingAccount();
        bankSavingAccount.setId(UUID.randomUUID().toString());
        bankSavingAccount.setCreatedAt(new Date());
        bankSavingAccount.setDuration(duration);
        bankSavingAccount.setBalance(initialBalance);
        bankSavingAccount.setCustomer(customer);

        SavingAccount savedBankAccount = bankAccountRepository.save(bankSavingAccount);
        return bankAccountMapper.fromSavingAccount(savedBankAccount);
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOs = bankAccounts.stream()
                .map(bankAccount -> {
                    if (bankAccount instanceof SavingAccount) {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        return bankAccountMapper.fromSavingAccount(savingAccount);
                    } else {
                        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                        return bankAccountMapper.fromCurrentAccount(currentAccount);
                    }
                })
                .collect(Collectors.toList());
        return bankAccountDTOs;
    }


    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Bank Accoutn Not Found");
        Page<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers = customerRepository.findByNameContains(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDTOS;
    }



    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream().map(customer ->bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
        // this ^ equvalent to this :
        /*
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer:customers){
            CustomerDTO customerDTO = bankAccountMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }*/
        return customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingAccount(savingAccount);
        }else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BalanceNotSufficentException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found"));
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficentException("Balance not Sufficent");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account Not Found"));

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException{
        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);
    }


}
