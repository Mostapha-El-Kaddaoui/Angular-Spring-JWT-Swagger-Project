package ma.enset.tresorory;

import ma.enset.tresorory.Entities.*;

import ma.enset.tresorory.Enums.AccountStatus;
import ma.enset.tresorory.Enums.OperationType;
import ma.enset.tresorory.Exceptions.CustomerNotFoundException;
import ma.enset.tresorory.Repositories.AccountOperationRepository;
import ma.enset.tresorory.Repositories.BankAccountRepository;
import ma.enset.tresorory.Repositories.CustomerRepository;
import ma.enset.tresorory.Service.BankAccountService;
import ma.enset.tresorory.dto.BankAccountDTO;
import ma.enset.tresorory.dto.CurrentAccountDTO;
import ma.enset.tresorory.dto.CustomerDTO;
import ma.enset.tresorory.dto.SavingAccountDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.Authenticator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class TresororyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TresororyApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, CustomerRepository customerRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Khalid").forEach(name->{
                CustomerDTO customerDTO=new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customerDTO);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*900, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*900, 3, customer.getId());


                } catch (CustomerNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            List<BankAccountDTO> bankAccountDTOS = bankAccountService.bankAccountList();
            for(BankAccountDTO bankAccountDTO: bankAccountDTOS){
                for(int i=0; i<10; i++){
                    String accountId;
                    if(bankAccountDTO instanceof SavingAccountDTO){
                        accountId = ((SavingAccountDTO) bankAccountDTO).getId();
                    }else{
                        accountId = ((CurrentAccountDTO) bankAccountDTO).getId();
                    }
                    bankAccountService.credit(accountId, 100000+Math.random()*12000,"Trans Credit");
                    bankAccountService.debit(accountId, 1000+Math.random()*12000,"Trans debit");

                }
            }

        };
    };
    //@Bean
//    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
//        return args -> {
//            BankAccount bankAcc = bankAccountRepository.findById("0124ad4a-a70d-4843-9117-6252e9ce0bcb").get();
//            System.out.println(bankAcc.getBalance());
//            System.out.println(bankAcc.getCustomer().getName());
//            System.out.println(bankAcc.getCustomer().getEmail());
//
//            bankAcc.getAccountOperations().forEach(accountOperation -> {
//                System.out.println(accountOperation.getAmount()+" "+accountOperation.getType()+" "+accountOperation.getId());
//            });
//        };
//    };
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args->{
            Stream.of("Hassan","Yassine","Khalid").forEach(name->{
                Customer customer = Customer.builder()
                        .name(name)
                        .email(name+"@gmail.com")
                        .build();

                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setDuration(100);
                bankAccountRepository.save(savingAccount);
            });
             bankAccountRepository.findAll().forEach(account -> {
                 for (int i = 0; i < 5; i++) {
                     AccountOperation accountOperation = AccountOperation.builder()
                             .operationDate(new Date())
                             .amount(Math.random()*12000)
                             .type(Math.random()>0.5?OperationType.DEBIT:OperationType.CREDIT)
                             .bankAccount(account)
                             .build();
                     accountOperationRepository.save(accountOperation);
                 }
             });

        };
    }






























}
