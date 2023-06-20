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

public class AdminLoginDto  {
@NotEmpty
private String username;
@NotEmpty
private String password;
private Boolean rememberMe = false;
private String fullname;
private String photo;
}
