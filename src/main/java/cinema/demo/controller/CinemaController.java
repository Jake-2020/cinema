package cinema.demo.controller;

import cinema.demo.entity.Cinema;
import cinema.demo.service.CinemaService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/seleceCinemaById")
    @ResponseBody
    public JSONObject seleceCinemaById(Integer id){
        JSONObject jsonObject = new JSONObject();
        Cinema cinema = cinemaService.selectCinemaById(id);
        jsonObject.put("msg","");
        jsonObject.put("code",200);
        jsonObject.put("data",cinema);

        return jsonObject;
    }

    @GetMapping("/selectAllCinemas")
    @ResponseBody
    public JSONObject selectAllCinemas(){
        JSONObject jsonObject = new JSONObject();
        List<Cinema> list = cinemaService.selectAllCinemas();
        jsonObject.put("msg","");
        jsonObject.put("code",200);
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);

        return jsonObject;
    }

    @GetMapping("/selectCinemasLikeName")
    @ResponseBody
    public JSONObject selectCinemasLikeName(String cinemaName){
        JSONObject jsonObject = new JSONObject();
        List<Cinema> list = cinemaService.selectCinemasLikeName(cinemaName);
        jsonObject.put("msg","");
        jsonObject.put("code",0);
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);

        return jsonObject;
    }

    @PostMapping("/insertCinema")
    @ResponseBody
    public JSONObject insertCinema(Cinema cinema){
        JSONObject jsonObject = new JSONObject();
        Cinema cinema1 = cinemaService.selectCinemasByName(cinema.getCinemaName());
        //System.out.println(cinema1);
        if(cinema1 != null){
            jsonObject.put("msg","该影院已存在！");
            jsonObject.put("code",200);
            jsonObject.put("data","");
        }else{
            jsonObject.put("msg","添加影院成功");
            jsonObject.put("code",0);
            jsonObject.put("data",cinemaService.insertCinema(cinema));
        }
        return jsonObject;
    }

    @PostMapping("/updateCinema")
    @ResponseBody
    public JSONObject updateCinema(Cinema cinema){
        JSONObject jsonObject = new JSONObject();
        Cinema cinema1 = cinemaService.selectCinemaById(cinema.getId());
       // System.out.println(cinema1.getCinemaName());
        Integer result = cinemaService.updateCinema(cinema);
        String name1 = cinema.getCinemaName();
        //System.out.println(name1);
        if(!cinema1.getCinemaName().equals(name1)){
            jsonObject.put("msg","修改影院成功!");
            jsonObject.put("code",200);
            jsonObject.put("data",cinema);
        }else{
            jsonObject.put("msg","修改影院失败!");
            jsonObject.put("code",400);
            jsonObject.put("data",cinema);
        }

        return jsonObject;
    }

    @PostMapping("/deleteCinema")
    @ResponseBody
    public JSONObject deleteCinema(Integer id){
        JSONObject jsonObject = new JSONObject();
        Integer result = cinemaService.deleteCinema(id);
        if(result > 0 ){
            jsonObject.put("msg","删除影院成功！");
            jsonObject.put("code",200);
        }else{
            jsonObject.put("msg","删除影院失败！");
            jsonObject.put("code",400);
        }
        return jsonObject;
    }


}
