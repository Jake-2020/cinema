package cinema.demo.dao;

import cinema.demo.entity.Cinema;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CinemaMapper {
    Cinema selectCinemaById(Integer id);

    Cinema selectCinemasByName(String cinemaName);

    List<Cinema> selectAllCinemas();

    List<Cinema> selectCinemasLikeName(String cinemaName);

    Integer insertCinema(Cinema cinema);

    Integer updateCinema(Cinema cinema);

    Integer deleteCinema(Integer id);

    List<Cinema> selectMovieByCinemaId(Integer id);
}
