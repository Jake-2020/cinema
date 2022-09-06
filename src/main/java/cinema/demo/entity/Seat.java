package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private Integer id;
    private Integer orderId;  //所属于的订单  //外键
    private Integer col;    //排
    private Integer row;    //座
}
