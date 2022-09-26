package cinema.demo.controller;

import cinema.demo.entity.Order;
import cinema.demo.entity.User;
import cinema.demo.service.MovieService;
import cinema.demo.service.OrderService;
import cinema.demo.service.ScheduleService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.thoughtworks.qdox.model.expression.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private MovieService movieService;

    @GetMapping("/selectOrderById")
    @ResponseBody
    public JSONObject selectOrderById(String id){
        JSONObject jsonObject = new JSONObject();
        Order order = orderService.selectOrderById(id);
       // System.out.println(order);
        List<Order> list = new ArrayList<Order>();
        list.add(order);
        jsonObject.put("code",200);
        jsonObject.put("msg","查询成功");
        jsonObject.put("count",list.size());
        jsonObject.put("data",list);

        return jsonObject;
    }
    @GetMapping("/selectOrderByUserName")
    @ResponseBody
    public JSONObject selectOrderByUserName(@RequestParam(value="page",defaultValue="1")Integer page, @RequestParam(value="limit",defaultValue="10")Integer limit, @RequestParam("username")String username) {
        PageInfo<Order> info = orderService.selectOrderByUserName(page, limit, username);
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "用户订单如下");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @GetMapping("/selectRefundOrderByUser")
    @ResponseBody
    public JSONObject selectRefundOrderByUser(@RequestParam("username")String username) {
        JSONObject obj = new JSONObject();
        List<Order> list = this.orderService.selectRefundOrderByUserName(username);
        obj.put("code", 0);
        obj.put("msg", "用户退票订单如下");
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }

    @GetMapping("/selectAllOrders")
    @ResponseBody
    public JSONObject selectAllOrders() {
        JSONObject obj = new JSONObject();
        List<Order> list = orderService.selectAllOrders();
        obj.put("code", 0);
        obj.put("msg", "所有订单如下");
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }

    @GetMapping("/selectAllOrdersPage")
    @ResponseBody
    public JSONObject selectAllOrdersPage(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit,String keyword) {
        PageInfo<Order> info = orderService.selectAllOrdersBySplitPage(page, limit, keyword);
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @GetMapping("/selectAllRefundOrder")
    @ResponseBody
    public JSONObject selectAllRefundOrder(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit) {
        JSONObject obj = new JSONObject();
        PageInfo<Order> info = orderService.selectOrdersByState(page, limit, 0);
        obj.put("code", 200);
        obj.put("msg", "退票订单如下");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @PostMapping("/buyTickets")
    @ResponseBody
    public JSONObject buyTickets(Integer scheduleId, String position[], Integer price, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            jsonObject.put("code",400);
            jsonObject.put("msg","您还未登录，请先登录!");
        }
        else{
            int count = 0;
            int orderPrice = price / position.length;
            String userId = "";
            switch(String.valueOf(user.getId()).length()) {
                case 1:  userId = "000" + String.valueOf(user.getId()); break;
                case 2: userId = "00" + String.valueOf(user.getId()); break;
                case 3: userId = "0" + String.valueOf(user.getId()); break;
                case 4: userId = String.valueOf(user.getId()); break;
            }
            for(int i=0;i<position.length;i++){
                Order order = new Order();
                String orderId = "";
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
                orderId += dateFormat.format(date);
                orderId += userId;
                String index = "";
                switch(position[i].length()) {
                    case 4:
                        index = "0" + position[i].replaceAll("排", "0");
                        index = index.replaceAll("座", "");
                        break;
                    case 5:
                        if(position[i].charAt(2) >= 48 && position[i].charAt(2) <= 57) {
                            index = "0" + position[i].replaceAll("排", "");
                            index = index.replaceAll("座", "");
                        }else {
                            index = position[i].replaceAll("排", "0");
                            index = index.replaceAll("座", "");
                        }
                        break;
                    case 6:
                        index = position[i].replaceAll("排", "");
                        index = index.replaceAll("座", "");
                        break;
                }
                orderId += index;
                order.setId(orderId);
                order.setOrderPosition(position[i]);
                order.setScheduleId(scheduleId);
                order.setUserId(user.getId());
                order.setOrderPrice(orderPrice);
                order.setOrderTime(new Date());
                Integer result = orderService.insertOrder(order);
                Integer result1 = scheduleService.delScheduleRemain(scheduleId);
                count++;
            }
            if(count == position.length) {
                float sum = (float)price/10000;
                Integer result2 = movieService.changeMovieBoxOffice(sum, scheduleService.selectScheduleById(scheduleId).getMovieId());
                jsonObject.put("code",200);
                jsonObject.put("msg", "购票成功");
            }else {
                jsonObject.put("code",400);
                jsonObject.put("msg", "购票失败");
            }
        }
        return jsonObject;
    }

    @PostMapping("/applyForRefund")
    @ResponseBody
    public JSONObject applyForRefund(@RequestParam("orderId")String orderId) {
        JSONObject jsonObject = new JSONObject();
        Integer result = orderService.updateOrderStateToRefund(orderId);
        if(result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "退票申请已发送");
        }else {
            jsonObject.put("code", 200);
            jsonObject.put("msg", "操作失败");
        }
        return jsonObject;
    }

    @PostMapping("/agreeForRefund")
    @ResponseBody
    public JSONObject agreeForRefund(@RequestParam("orderId")String orderId) {
        JSONObject jsonObject = new JSONObject();
        Integer result = this.orderService.updateOrderStateToRefunded(orderId);
        if(result > 0) {
            Order order = this.orderService.selectOrderById(orderId);
            int price = order.getOrderPrice();
            int movieId = order.getOrderSchedule().getMovieId();
            Integer result1 = movieService.changeMovieBoxOffice((float)price/10000, movieId);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "退票成功");
        }else {
            jsonObject.put("code", 200);
            jsonObject.put("msg", "退票失败");
        }
        return jsonObject;
    }

}
