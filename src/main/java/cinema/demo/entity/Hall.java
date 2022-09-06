package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall{
    private Integer id;
    private String hallName;    //放映厅的名称
    private Integer hallCapacity;   //放映厅的容量
    private Integer cinemaId;       //放映厅所属于的电影  //外键
    private Cinema hallCinema;      //所属的影院
    private List<Schedule> scheduleList;    //所有的电影场次集合
}
