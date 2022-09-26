package cinema.demo.dao;

import cinema.demo.entity.Order;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order selectOrderById(String id);
    Integer insertOrder(Order order);
    Integer updateOrderStateToRefund(String id); //申请退票
    Integer updateOrderStateToRefunded(String id); //同意退票
    List<Order> selectOrdersByUserName(String username);
    List<Order> selectAllOrders();
    List<Order> selectRefundOrderByUserName(String username);
    List<Order> selectOrdersByState(@Param("page")Integer page,@Param("limit")Integer limit,@Param("orderState") Integer orderState);
    PageInfo<Order> selectAllOrdersBySplitPage(Integer page,Integer limit,String keyword);
    List<Order> selectOrdersByScheduleId(Integer scheduleId);
}
