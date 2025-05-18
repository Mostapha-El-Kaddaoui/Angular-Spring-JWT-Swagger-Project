package ma.enset.tresorory.Web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.tresorory.Exceptions.CustomerNotFoundException;
import ma.enset.tresorory.Mappers.BankAccountMapperImpl;
import ma.enset.tresorory.Service.BankAccountService;
import ma.enset.tresorory.dto.CustomerDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private final BankAccountMapperImpl bankAccountMapperImpl;
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('ROLE_USER')")// Modifié de hasAuthority('SCOPE_USER')
    public List<CustomerDTO> listCustomers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/search")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return bankAccountService.searchCustomers(keyword);
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCustomer(@PathVariable(name = "id") Long customerId){
        bankAccountService.deleteCustomer(customerId);
    }

}
