package cinema.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Integer id;
    private String movieCName;   //中文名字
    private String movieFName;   //英文名字
    private String movieActor;     //电影演员
    private String movieDirector;  //电影导演
    private String movieDetail;    //电影详情
    private String movieDurantion; //电影时长
    private Integer movietype;        //电影类型
    private Float movieScore;      //电影评分
    private Float movieBoxOffice;  //电影票房
    private Date movieReleaseDate; //电影上映时间
    private Integer movieState;          //电影状态
    private List<Comment> commentList;
}
