package cinema.demo.service;

import cinema.demo.dao.CinemaMapper;
import cinema.demo.dao.HallMapper;
import cinema.demo.entity.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {

    @Autowired
    private HallMapper hallMapper;

    @Autowired
    private CinemaMapper cinemaMapper;

    public Hall selectHallById(Integer id){
        Hall hall =  hallMapper.selectHallById(id);
        hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
        return hall;
    }

    public List<Hall> selectAllHalls(){
        List<Hall> list = hallMapper.selectAllHalls();
        for(Hall hall : list) {
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
        }
        return list;
    }

    public Integer addHall(Hall hall){
        return hallMapper.addHall(hall);
    }

    public Integer updateHall(Hall hall){
        return hallMapper.updateHall(hall);
    }

    public Integer deleteHall(Integer id){
        return hallMapper.deleteHall(id);
    }

    public List<Hall> selectHallByCinemaId(Integer cimemaId){
        List<Hall> list = hallMapper.selectHallByCinemaId(cimemaId);
        for(Hall hall : list) {
            hall.setHallCinema(cinemaMapper.selectCinemaById(hall.getCinemaId()));
        }
        return list;
    }

    public Hall selectHallByCinemaAndHallName(String cinemaName, String hallName) {
        return hallMapper.findHallByCinemaAndHallName(cinemaName, hallName);
    }
}
