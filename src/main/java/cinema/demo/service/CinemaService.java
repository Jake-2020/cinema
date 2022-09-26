package cinema.demo.service;

import cinema.demo.dao.CinemaMapper;
import cinema.demo.entity.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

@Service
public class CinemaService implements CinemaMapper{
    @Autowired
    private CinemaMapper cinemaMapper;

    public Cinema selectCinemaById(Integer id){
        Cinema cinema = this.cinemaMapper.selectCinemaById(id);
        return cinema;
    }

    public  Cinema selectCinemasByName(String cinemaName){
        return cinemaMapper.selectCinemasByName(cinemaName);
    }

    public List<Cinema> selectAllCinemas(){
        List<Cinema> list = cinemaMapper.selectAllCinemas();
        return list;
    }

    public List<Cinema> selectCinemasLikeName(String cinemaName){
        List<Cinema> list = cinemaMapper.selectCinemasLikeName(cinemaName);
        return list;
    }

    public Integer insertCinema(Cinema cinema){
       return  cinemaMapper.insertCinema(cinema);
    }

    public Integer updateCinema(Cinema cinema){
        return cinemaMapper.updateCinema(cinema);
    }

    public Integer deleteCinema(Integer id){
        return cinemaMapper.deleteCinema(id);
    }

    public List<Cinema> selectMovieByCinemaId(Integer id){return cinemaMapper.selectMovieByCinemaId(id);}
}
