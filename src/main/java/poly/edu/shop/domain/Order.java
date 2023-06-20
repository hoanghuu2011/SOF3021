package poly.edu.shop.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="orders")
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int orderId;
	@Temporal(TemporalType.DATE)
private Date orderDate;
//	@Column(nullable = false)
//private int customerId;
	@Column(nullable = false)
private double amout;
	@Column(nullable = false)
private short status;
	@Column(name = "address", nullable = false)
	private String address;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Account account;

	
	@ManyToOne
	@JoinColumn(name ="customerId")
	private Customer customer;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails;
	
	
}
