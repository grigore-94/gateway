package md.bro.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import md.bro.gateway.entity.Role;
import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
  private Integer id;
  private String username;
  private String email;
  private int rank;
  private List<Role> roles;
}
