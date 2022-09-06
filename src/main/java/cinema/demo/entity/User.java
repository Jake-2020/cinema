package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer type;      //类型  0为普通管理员  1为超级管理员
    private String headUrl;    //头像url
    private Date createTime;   //注册时间
    private String salt;        //盐值
}
