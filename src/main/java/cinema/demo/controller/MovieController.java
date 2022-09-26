package cinema.demo.controller;

import cinema.demo.entity.Cinema;
import cinema.demo.entity.Movie;
import cinema.demo.service.CinemaService;
import cinema.demo.service.MovieService;
import cinema.demo.util.CinemaUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @Value("${cinema.path.movie}")
    private String uploadPath;

    @Value("${cinema.path.domain}")
    private String domain;


    @GetMapping("/selectMovieById")
    @ResponseBody
    public JSONObject selectMovieById(Integer id) {
        JSONObject obj = new JSONObject();
        Movie movie = movieService.selectMovieById(id);
        List<Cinema> list = this.cinemaService.selectMovieByCinemaId(id);
        obj.put("code", 200);
        obj.put("data", movie);
        obj.put("cinemaList",list);
        obj.put("cinemaCount",list.size());
        return obj;
    }

    @GetMapping("/selectAllMovies")
    @ResponseBody
    public JSONObject selectAllMovies(){
        JSONObject jsonObject = new JSONObject();
        List<Movie> commit = movieService.selectAllMovies(1);  //上映的电影
        //System.out.println(commit);
        List<Movie> uncommit = movieService.selectAllMovies(0);    //即将上映的电影
        //System.out.println(uncommit);
        List<Movie> moviebybox = movieService.selectMovieByBoxOffice();       //根据票房排名的电影集
        //System.out.println(moviebybox);
        String type[] = {"喜剧","动作","爱情","动画","科幻","惊悚","冒险","犯罪","悬疑"}; //电影类型
        ArrayList<Object> arrayList = new ArrayList<Object>();

        for(int i = 0;i<type.length;i++){
            List<Movie> movieList = movieService.selectMovieLikeType(type[i]);
            Float boxOffice = 0.0f;
            for(int j=0;j<movieList.size();j++){
                boxOffice += movieList.get(j).getMovieBoxOffice();
            }
            JSONObject json = new JSONObject();
            json.put(type[i],boxOffice);
            arrayList.add(json);
        }
        jsonObject.put("code",200);
        jsonObject.put("count",commit.size());
        jsonObject.put("uncommit",uncommit);
        jsonObject.put("data",commit);
        jsonObject.put("sort",moviebybox);
        jsonObject.put("type",type);
        return jsonObject;
    }

    @GetMapping("/selectMovieByName")
    @ResponseBody
    public JSONObject selectMovieByName(String name){
        JSONObject jsonObject = new JSONObject();
        List<Movie> list = movieService.selectMovieLikeName(name);

        jsonObject.put("code",200);
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);

        return jsonObject;
    }

    @GetMapping("/selectMovieByType")
    @ResponseBody
    public JSONObject selectMovieByType(String type){
        JSONObject jsonObject = new JSONObject();
        List<Movie> list = movieService.selectMovieLikeType(type);

        jsonObject.put("code",200);
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);

        return jsonObject;
    }

    @GetMapping("/sortAllMovie")
    @ResponseBody
    public JSONObject sortAllMovie(String name){
        JSONObject jsonObject = new JSONObject();
        List<Movie> list = new ArrayList<>();

        switch (name){
            case "热门" : list = movieService.sortMovieBycomentCount();
            case "时间" : list = movieService.sortMovieByDate();
            case "评价" : list = movieService.sortMovieByScore();
        }
        jsonObject.put("code",200);
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);
        return jsonObject;
    }

    @PostMapping("/deleteMovie")
    @ResponseBody
    public JSONObject deleceMovie(Integer id){
        JSONObject jsonObject = new JSONObject();
        Integer result = movieService.deleceMovie(id);

        if(result > 0 ){
            jsonObject.put("code",200);
            jsonObject.put("msg","下架电影成功！");
        }else{
            jsonObject.put("code",400);
            jsonObject.put("msg","下架电影失败！");
        }

        return jsonObject;
    }

    @PostMapping("/insertMovie")
    @ResponseBody
    public JSONObject insertMovie(MultipartFile file, Movie movie) throws IOException {
        JSONObject jsonObject = new JSONObject();

        List<Movie> list = movieService.selectMovieLikeName(movie.getMovieCName());
        for(Movie movie1 : list){
            if(movie1.getMovieCName().equals(movie.getMovieCName())){
                jsonObject.put("code",400);
                jsonObject.put("msg","影库中已有相同名字的电影!");
                return jsonObject;
            }
        }
        //获取图片后缀（png/jpg/jpeg）
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //生成随机文件名
        fileName = CinemaUtil.getUUID() +'.'+ suffix;
        //确定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        //存储文件
        file.transferTo(dest);
        String moviePicture = domain +"/user/movie/" +fileName;
        System.out.println(moviePicture);
        Integer result1 = movieService.insertMovie(movie);
        Integer result2 = movieService.insertMoviePicture(movie.getMovieCName(),moviePicture);
        System.out.println(movie);
        System.out.println(result2);
        if(result1 > 0){
            jsonObject.put("code",200);
            jsonObject.put("msg","上架电影成功");
        }else{
            jsonObject.put("code",400);
            jsonObject.put("msg","上架电影失败");
        }
        return jsonObject;
    }

    @PostMapping("/updateMovie")
    @ResponseBody
    public JSONObject updateMovie(MultipartFile file,Movie movie) throws IOException {
        JSONObject jsonObject = new JSONObject();

        List<Movie> list = movieService.selectMovieLikeName(movie.getMovieCName());
        for(Movie movie1 : list){
            if(movie1.getMovieCName().equals(movie.getMovieCName())){
                jsonObject.put("code",400);
                jsonObject.put("msg","影库中已有相同名字的电影!");
                return jsonObject;
            }
        }
        //获取图片后缀（png/jpg/jpeg）
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //生成随机文件名
        fileName = CinemaUtil.getUUID() +'.'+ suffix;
        //确定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        //存储文件
        file.transferTo(dest);
        String moviePicture = domain +"/user/movie/" +fileName;
        System.out.println(moviePicture);
        Integer result1 = movieService.updateMovie(movie);
        Integer result2 = movieService.updateMoviePicture(movie.getId(),moviePicture);
        System.out.println(movie);
        System.out.println(result2);
        if(result1 > 0){
            jsonObject.put("code",200);
            jsonObject.put("msg","修改电影成功");
        }else{
            jsonObject.put("code",400);
            jsonObject.put("msg","修改电影失败");
        }
        return jsonObject;
    }
}
