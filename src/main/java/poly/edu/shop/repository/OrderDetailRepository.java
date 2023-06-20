package poly.edu.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import poly.edu.shop.domain.OrderDetail;
import poly.edu.shop.model.ReportCategory;



@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	@Query("select od from OrderDetail od where od.order.id=:orderId")
	List<OrderDetail> findByOrderId(@Param("orderId") Integer orderId);

	@Query("select new ReportCategory(od.product.category, sum(od.unitPrice*od.quantity), sum(od.quantity)) from OrderDetail od group by od.product.category")
	List<ReportCategory> reportByCategory();

}