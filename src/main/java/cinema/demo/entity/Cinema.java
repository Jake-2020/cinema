package cinema.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cinema {
    private Integer id;
    private String cinemaName;  //影院名称
    private String cinemaAddress;   //影院地址
    private List<Hall> hallList;    //影院的放映厅集合
}
