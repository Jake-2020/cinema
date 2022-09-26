package cinema.demo.service;

import cinema.demo.dao.*;
import cinema.demo.entity.Hall;
import cinema.demo.entity.Order;
import cinema.demo.entity.Schedule;
import cinema.demo.entity.User;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thoughtworks.qdox.model.expression.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private CinemaMapper cinemaMapper;

    public Order selectOrderById(String id) {
        Order order = orderMapper.selectOrderById(id);
        //System.out.println(id);
        //System.out.println(order);
        if(order != null) {
            order.setOrderUser(userMapper.selectUserById(order.getUserId()));
            Schedule schedule = scheduleMapper.selectScheduleById(order.getScheduleId());
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            order.setOrderSchedule(schedule);
        }else {
            order = null;
        }
        return order;
    }

    public PageInfo<Order> selectOrderByUserName(Integer page,Integer limit,String username){
        PageHelper.startPage(page,limit);
        List<Order> list = orderMapper.selectOrdersByUserName(username);
            for(Order order : list){
                order.setOrderUser(userMapper.selectUserById(order.getUserId()));
                Schedule schedule = scheduleMapper.selectScheduleById(order.getScheduleId());
                Hall hall = hallMapper.selectHallById(schedule.getHallId());
                hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
                schedule.setScheduleHall(hall);
                schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
                order.setOrderSchedule(schedule);
            }
        PageInfo<Order> pageInfo = new PageInfo<Order>(list);
        return pageInfo;
    }

    public List<Order> selectRefundOrderByUserName(String username){
        List<Order> list = orderMapper.selectRefundOrderByUserName(username);
        if(list.size()>0) {
            for (Order order : list) {
                order.setOrderUser(userMapper.selectUserById(order.getUserId()));
                Schedule schedule = scheduleMapper.selectScheduleById(order.getScheduleId());
                Hall hall = hallMapper.selectHallById(schedule.getHallId());
                hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
                schedule.setScheduleHall(hall);
                schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
                order.setOrderSchedule(schedule);
            }
        }else{
            list = null;
        }
        return list;
    }

    public List<Order> selectAllOrders() {
        List<Order> list = this.orderMapper.selectAllOrders();
        for(Order order : list) {
            order.setOrderUser(userMapper.selectUserById(order.getUserId()));
            Schedule schedule = scheduleMapper.selectScheduleById(order.getScheduleId());
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            order.setOrderSchedule(schedule);
        }
        return list;
    }

    public PageInfo<Order> selectAllOrdersBySplitPage(Integer page, Integer limit, String keyword) {
        PageHelper.startPage(page, limit);
        List<Order> list = new ArrayList<Order>();
        if(keyword != null && !keyword.trim().equals("")) {
            list = this.orderMapper.selectOrdersByUserName(keyword);
        }else {
            list = this.orderMapper.selectAllOrders();
        }
        for(Order order : list) {
            order.setOrderUser(userMapper.selectUserById(order.getUserId()));
            Schedule schedule = scheduleMapper.selectScheduleById(order.getScheduleId());
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            order.setOrderSchedule(schedule);
        }
        PageInfo<Order> info = new PageInfo<Order>(list);
        return info;
    }

    public PageInfo<Order> selectOrdersByState(Integer page,Integer limit,Integer orderState) {
        PageHelper.startPage(page, limit);
        List<Order> list =  orderMapper.selectOrdersByState(page,limit,orderState);
        for(Order order : list) {
            order.setOrderUser(userMapper.selectUserById(order.getUserId()));
            Schedule schedule = scheduleMapper.selectScheduleById(order.getScheduleId());
            Hall hall = hallMapper.selectHallById(schedule.getHallId());
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
            schedule.setScheduleHall(hall);
            schedule.setScheduleMovie(movieMapper.selectMovieById(schedule.getMovieId()));
            order.setOrderSchedule(schedule);
        }
        PageInfo<Order> info = new PageInfo<Order>(list);
        return info;
    }

    public Integer insertOrder (Order order){
        return orderMapper.insertOrder(order);
    }


    public Integer updateOrderStateToRefund(String id) {
        return orderMapper.updateOrderStateToRefund(id);
    }

    public Integer updateOrderStateToRefunded(String id) {
        return this.orderMapper.updateOrderStateToRefunded(id);
    }

}

