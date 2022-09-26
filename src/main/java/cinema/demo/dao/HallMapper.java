package cinema.demo.dao;

import cinema.demo.entity.Hall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HallMapper {

    Hall selectHallById(Integer id);
    Hall findHallByCinemaAndHallName(String cinema_name,String hall_name);
    Integer addHall(Hall hall);
    Integer updateHall(Hall hall);
    Integer deleteHall(Integer id);
    List<Hall> selectHallByCinemaId(Integer cimemaId);
    List<Hall> selectAllHalls();
}
