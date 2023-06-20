package poly.edu.shop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poly.edu.shop.domain.AccountRole;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDto  {
@NotEmpty
@Length(min= 4)
private String username;
@NotEmpty
@Length(min= 4)
private String password;
private String fullname;
private Boolean isEdit = false;
private AccountRole role;
private String currentPassword;
private String newPassword;
}
