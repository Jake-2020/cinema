package cinema.demo.controller;

import cinema.demo.entity.*;
import cinema.demo.service.CinemaService;
import cinema.demo.service.HallService;
import cinema.demo.service.MovieService;
import cinema.demo.service.ScheduleService;
import cinema.demo.util.CinemaUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private HallService hallService;

    @GetMapping("/selectScheduleById")
    @ResponseBody
    public JSONObject selectScheduleById(Integer id){
        JSONObject jsonObject = new JSONObject();

        Schedule schedule = scheduleService.selectScheduleById(id);
        jsonObject.put("code",200);
        jsonObject.put("data",schedule);
        return jsonObject;
    }

    @GetMapping("/selectScheduleByState")
    @ResponseBody
    public JSONObject selectScheduleByState(@RequestParam(value="page",defaultValue="1")Integer page, @RequestParam(value="limit",defaultValue="10")Integer limit, @RequestParam("scheduleState")Integer scheduleState){
        JSONObject jsonObject = new JSONObject();
        PageInfo<Schedule> info = scheduleService.selectScheduleByState(page, limit, scheduleState);
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int j = 0;j < info.getList().size();j++) {
            List<Order> orderList = info.getList().get(j).getOrderList();
            int income = 0;
            for(int i = 0;i < orderList.size();i++) {
                income += orderList.get(i).getOrderPrice();
            }
            incomeArr.add(income);
        }
        jsonObject.put("code", 0);
        jsonObject.put("count", info.getTotal());
        jsonObject.put("data", info.getList());
        jsonObject.put("income", incomeArr);
        return jsonObject;
    }

    @GetMapping("/selectAllSchedule")
    @ResponseBody
    public JSONObject selectAllSchedule(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit){
        JSONObject jsonObject = new JSONObject();
        PageInfo<Schedule> info = scheduleService.selectAllSchedule(page,limit);
        List<Movie> movieList = movieService.selectAllMovies(1);
        List<Cinema> cinemaList = cinemaService.selectAllCinemas();
        ArrayList<String> movieArr = new ArrayList<String>();
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int i = 0;i < info.getList().size();i++) {
            List<Order> orderList = info.getList().get(i).getOrderList();
            int income = 0;
            for(int j = 0;j < orderList.size();j++) {
                income += orderList.get(j).getOrderPrice();
            }
            incomeArr.add(income);
        }
        for(int i = 0;i < movieList.size();i++) {
            movieArr.add(movieList.get(i).getMovieCName());
        }

        ArrayList<Object> cinema = new ArrayList<>();
        for(int i = 0;i < cinemaList.size();i++) {
            JSONObject cinemaObj = new JSONObject();
            List<Hall> hallList = hallService.selectHallByCinemaId(cinemaList.get(i).getId());
            ArrayList<String> hallArr = new ArrayList<String>();
            for(int j = 0;j < hallList.size();j++) {
                hallArr.add(hallList.get(j).getHallName());
            }
            cinemaObj.put(cinemaList.get(i).getCinemaName(), hallList);
            cinema.add(cinemaObj);
        }

        jsonObject.put("code", 200);
        jsonObject.put("count", info.getTotal());
        jsonObject.put("data", info.getList());
        jsonObject.put("movieName", movieArr);
        jsonObject.put("cinema", cinema);
        jsonObject.put("income", incomeArr);
        return jsonObject;
    }

    @GetMapping("/selectScheduleByName")
    @ResponseBody
    public JSONObject selectScheduleByName(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit,@RequestParam("movieCName")String movieCName){
        JSONObject jsonObject = new JSONObject();
        PageInfo<Schedule> info = scheduleService.selectScheduleByMovieName(page,limit,movieCName);
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int j = 0;j < info.getList().size();j++) {
            List<Order> orderList = info.getList().get(j).getOrderList();
            int income = 0;
           // System.out.println(info.getList().get(j));
            //System.out.println(orderList);
            for(int i = 0;i < orderList.size();i++) {
                income += orderList.get(i).getOrderPrice();
            }
            //System.out.println(income);
            incomeArr.add(income);
        }
        jsonObject.put("code", 200);
        jsonObject.put("count", info.getTotal());
        jsonObject.put("data", info.getList());
        jsonObject.put("income", incomeArr);
        return jsonObject;
    }

    @GetMapping("/selectOffScheduleByMovieName")
    @ResponseBody
    public JSONObject selectOffScheduleByMovieName(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit,@RequestParam("movieCName")String movieCName) {
        JSONObject obj = new JSONObject();
        PageInfo<Schedule> info = scheduleService.selectOffScheduleByMovieName(page, limit, movieCName);
        ArrayList<Integer> incomeArr = new ArrayList<Integer>();
        for(int j = 0;j < info.getList().size();j++) {
            List<Order> orderList = info.getList().get(j).getOrderList();
            int income = 0;
            for(int i = 0;i < orderList.size();i++) {
                income += orderList.get(i).getOrderPrice();
            }
            incomeArr.add(income);
        }
        obj.put("code", 200);
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        obj.put("income", incomeArr);
        return obj;
    }

    @PostMapping("/insertSchedule")
    @ResponseBody
    public JSONObject insertSchedule(@RequestParam("movieName")String movieName,@RequestParam("hallName")String hallName,@RequestParam("cinemaName")String cinemaName,
                                  @RequestParam("schedulePrice")int schedulePrice,@RequestParam("scheduleStartTime")String scheduleStartTime) {
        JSONObject jsonObject = new JSONObject();
        Schedule schedule = new Schedule();
        Hall hall = hallService.selectHallByCinemaAndHallName(cinemaName, hallName);
        schedule.setMovieId(this.movieService.selectMovieByName(movieName).getId());
        schedule.setHallId(hall.getId());
        schedule.setSchedulePrice(schedulePrice);
        schedule.setScheduleStartTime(scheduleStartTime);
        schedule.setScheduleRemain(hall.getHallCapacity());
        Integer result = scheduleService.insertSchedule(schedule);
        if(result > 0) {
            jsonObject.put("code", 200);
            jsonObject.put("mgs", "添加电影场次成功");
        }else {
            jsonObject.put("code", 400);
            jsonObject.put("mgs", "添加电影场次失败");
        }
        return jsonObject;
    }
}
