package poly.edu.shop.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poly.edu.shop.domain.Category;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCategory implements Serializable {
	
	@Id
	private Category loai;
	private Double doanhThu;
	private Long soLuong;
	
}
