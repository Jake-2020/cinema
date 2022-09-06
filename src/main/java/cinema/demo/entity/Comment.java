package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment{
    private Integer id;
    private Integer userId;     //所属于的用户ID    外键
    private String  commentContent; //评论的内容
    private Integer movieId;        //评论所属电影ID  外键
    private Date commentTime;       //评论创建时间
    private User commentUser;       //评论所属于的用户对象
}
