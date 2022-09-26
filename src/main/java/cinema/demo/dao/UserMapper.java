package cinema.demo.dao;

import cinema.demo.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectUserById(int id);

    User login(String username,String password );

    Integer insertUser(User user);

    Integer updateUser(@Param("id")Integer id,@Param("user") User user);

    List<User> selectAllUser();

    List<User> selectUserByName(String username);

    List<User> selectUserLikeName(String keyword);

    PageInfo<User> selectAllUserByPage(Integer page,Integer limit,String keyword);

    Integer updateHeader (@Param("id")Integer id,@Param("header")String header);
}
