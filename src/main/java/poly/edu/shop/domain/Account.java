package poly.edu.shop.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="accounts")
public class Account implements Serializable {
	@Id
	@Column(length = 30)
private String username;
	@Column(length = 60, nullable = false)
private String password;
	@Column(name = "fullname")
	private String fullname;
	@Column(name = "photo")
	private String photo;
	@Column(name = "role")
	private AccountRole role;
	@OneToMany(mappedBy = "account")
	private List<Order> orders;
}
