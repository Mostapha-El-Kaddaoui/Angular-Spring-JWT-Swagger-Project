package ma.enset.tresorory.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
}
