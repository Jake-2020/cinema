package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private  Integer id;
    private  Integer userId;    //订单编号
    private  Integer scheduleId;    //场次编号      //外键
    private     List<Seat> seatList;        //座位集合
    private  Integer orderState;    //订单状态
    private  Integer orderPrice;    //订单价格
    private  Integer orderTime;     //订单支付时间
    private  User orderUser;        //订单所属的对象
    private Schedule orderSchedule; //订单所属于的场次
}
