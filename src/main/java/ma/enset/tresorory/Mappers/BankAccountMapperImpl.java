package ma.enset.tresorory.Mappers;

import ma.enset.tresorory.Entities.AccountOperation;
import ma.enset.tresorory.Entities.CurrentAccount;
import ma.enset.tresorory.Entities.Customer;
import ma.enset.tresorory.Entities.SavingAccount;
import ma.enset.tresorory.dto.AccountOperationDTO;
import ma.enset.tresorory.dto.CurrentAccountDTO;
import ma.enset.tresorory.dto.CustomerDTO;
import ma.enset.tresorory.dto.SavingAccountDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();

        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail());

        // On peut utiliser cette fonctionalite de spring (source, destination)
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();

        //customer.setId(customerDTO.getId());
        //customer.setName(customerDTO.getName());
        //customer.setEmail(customer.getEmail());

        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public SavingAccountDTO fromSavingAccount(SavingAccount savingAccount){
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingAccountDTO);
        savingAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        savingAccountDTO.setType(savingAccount.getClass().getSimpleName());

        return savingAccountDTO;
    }
    public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));

        return savingAccount;
    }

    public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentAccountDTO;
    }
    public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));

        return currentAccount;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }
}
