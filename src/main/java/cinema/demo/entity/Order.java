package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private  String id;
    private  Integer userId;    //订单编号
    private  Integer scheduleId;    //场次编号      //外键
    private String orderPosition; //电影票座位信息
    private  Integer orderState;    //订单状态    1为正常  0为退票中  2为退票成功
    private  Integer orderPrice;    //订单价格
    private Date orderTime;     //订单支付时间
    private  User orderUser;        //订单所属的对象
    private Schedule orderSchedule; //订单所属于电影的场次
}
