package cinema.demo.service;

import cinema.demo.dao.*;
import cinema.demo.entity.Hall;
import cinema.demo.entity.Order;
import cinema.demo.entity.Schedule;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private HallMapper hallMapper;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private OrderMapper orderMapper;

    public Schedule selectScheduleById(Integer id){
        Schedule schedule = scheduleMapper.selectScheduleById(id);
        Hall hall = hallMapper.selectHallById(schedule.getHallId());
        hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
        schedule.setScheduleHall(hall);
        schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
        List<Order> list = orderMapper.selectOrdersByScheduleId(schedule.getId());
        schedule.setOrderList(list);
        return schedule;
    }

    public PageInfo<Schedule> selectScheduleByState(Integer page, Integer limit, Integer scheduleState){
        return scheduleMapper.selectScheduleByState(page,limit,scheduleState);
    }

    public Integer delScheduleRemain(Integer scheduleId){
        return scheduleMapper.delScheduleRemain(scheduleId);
    }

    public PageInfo<Schedule> selectAllSchedule(Integer page, Integer limit){
        PageHelper.startPage(page, limit);
        List<Schedule> schedules = scheduleMapper.selectAllSchedule();
        for(Schedule schedule: schedules) {
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            List<Order> list = orderMapper.selectOrdersByScheduleId(schedule.getId());
            schedule.setOrderList(list);
        }
        PageInfo<Schedule> info = new PageInfo<Schedule>(schedules);
        return info;
    }

    public PageInfo<Schedule> selectScheduleByMovieName(Integer page,Integer limit,String movieCName) {
        PageHelper.startPage(page, limit);
        List<Schedule> schedules = scheduleMapper.selectScheduleByMovieName(movieCName);
        for(Schedule schedule: schedules) {
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            List<Order> list = orderMapper.selectOrdersByScheduleId(schedule.getId());
            //System.out.println(list);
            schedule.setOrderList(list);
        }
        PageInfo<Schedule> info = new PageInfo<Schedule>(schedules);
        return info;
    }

    public PageInfo<Schedule> selectOffScheduleByMovieName(Integer page, Integer limit, String movieCName) {
        PageHelper.startPage(page, limit);
        List<Schedule> schedules = scheduleMapper.selectOffScheduleByMovieName(movieCName);
        for(Schedule schedule: schedules) {
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            List<Order> list = orderMapper.selectOrdersByScheduleId(schedule.getId());
            schedule.setOrderList(list);
        }
        PageInfo<Schedule> info = new PageInfo<Schedule>(schedules);
        return info;
    }

    public Integer insertSchedule(Schedule schedule) {
        return this.scheduleMapper.insertSchedule(schedule);
    }

}
