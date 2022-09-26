package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule{
    private Integer id;
    private Integer hallId;  //场次所属于的放映厅   //外键
    private Integer movieId;    //场次所属于的电影
    private String  scheduleStartTime;  //场次开始的时间
    private Integer schedulePrice;      //场次售价
    private Integer scheduleRemain;     //场次剩余座位
    private Integer scheduleState;      //场次状态：0未上映  1上映中
    private Hall scheduleHall;          //所属的放映厅对象
    private Movie scheduleMovie;        //所属于的电影对象
    private List<Order> orderList;      //所有的订单集合
}
