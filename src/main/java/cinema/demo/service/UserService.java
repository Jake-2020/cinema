package cinema.demo.service;

import cinema.demo.dao.LoginTicketMapper;
import cinema.demo.dao.UserMapper;
import cinema.demo.entity.LoginTicket;
import cinema.demo.entity.User;
import cinema.demo.util.CinemaUtil;
import cinema.demo.util.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
public class UserService implements Constant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    public User selectUserById(int id) {
        return userMapper.selectUserById(id);
    }

    public User login(String username,String password){
        //System.out.println(username);
        //System.out.println(password);
        List<User> userList = userMapper.selectUserByName(username);
        //System.out.println(userList);
        for(User user : userList) {
            //System.out.println(user.getUsername());
            if(user.getPassword().equals(password)) {
                //LoginTicket loginTicket = new LoginTicket();
                //loginTicket.setUserId(user.getId());
                //loginTicket.setTicket(CinemaUtil.getUUID());
                //loginTicket.setStatus(0);
                //loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));  //当前时间后移
                //loginTicketMapper.insertLoginTicket(loginTicket);

                return user;
            }
        }
        return null;
        }

    public Integer updateUser(Integer id,User user){
        //System.out.println(user);
        return userMapper.updateUser(id,user);
    }

    public Integer insertUser(User user){
        return this.userMapper.insertUser(user);
    }


    public List<User> selectAllUser(){
        return this.userMapper.selectAllUser();
    }

    public List<User> selectUserByName(String username){
        return userMapper.selectUserByName(username);
    }

    public List<User> selectUserLikeName(String username){
        return userMapper.selectUserLikeName(username);
    }

    public PageInfo<User> selectAllUserByPage(Integer page, Integer limit, String keyword) {
        PageHelper.startPage(page, limit);
        List<User> list = new ArrayList<User>();
        if(keyword != null && !keyword.trim().equals("")) {
            list = this.userMapper.selectUserLikeName(keyword);
            System.out.println(userMapper.selectUserLikeName(keyword));
        }else {
            list = this.userMapper.selectAllUser();
        }
        PageInfo<User> info = new PageInfo<User>(list);
        return info;
    }

    public User selectUserById(Integer id){
        return userMapper.selectUserById(id);
    }

    public Integer updateHeader(Integer id,String header){
        return userMapper.updateHeader(id,header);
    }

    public LoginTicket findLoginTicket(String ticket){
        return loginTicketMapper.selectByTicket(ticket);
    }


    //获取用户权限
    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        User user = this.selectUserById(userId);
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getType()) {
                    case 0:
                        return AUTHORITY_ADMIN;
                    default:
                        return AUTHORITY_USER;
                }
            }
        });
        return list;
    }
}
