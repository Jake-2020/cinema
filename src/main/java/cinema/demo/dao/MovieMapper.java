package cinema.demo.dao;

import cinema.demo.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {

    Movie selectMovieById(Integer id);

    Movie selectMovieByName(String name);

    Integer insertMovie(Movie movie);

    Integer deleceMovie(Integer id);

    Integer updateMovie(Movie movie);

    List<Movie> selectAllMovies(Integer movieState);

    List<Movie> selectMovieLikeName(String movieCName);

    List<Movie> selectMovieLikeType(String movieType);

    List<Movie> sortMovieByDate();

    List<Movie> sortMovieBycomentCount();

    List<Movie> selectMovieByBoxOffice();

    List<Movie> selectMovieByScore();

    Integer addMovieCommentCount(Integer id);

    Integer reduceMovieCommentCount(Integer id);

    Integer changeMovieBoxOffice(@Param("movieBoxOffice") Float movieBoxOffice,@Param("id") Integer id);

    Integer insertMoviePicture(@Param("name") String name,@Param("moviePicture") String moviePicture);

    Integer updateMoviePicture(@Param("id")Integer id,@Param("moviePicture") String moviePicture);
}
