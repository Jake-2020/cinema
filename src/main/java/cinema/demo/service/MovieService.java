package cinema.demo.service;

import cinema.demo.dao.MovieMapper;
import cinema.demo.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;

    public Movie selectMovieById(Integer id){
        return movieMapper.selectMovieById(id);
    }

    public Movie selectMovieByName(String name){
        return movieMapper.selectMovieByName(name);
    }

    public Integer insertMovie(Movie movie){
        return movieMapper.insertMovie(movie);
    }

    public Integer deleceMovie(Integer id){
        return movieMapper.deleceMovie(id);
    }

    public Integer updateMovie(Movie movie){
        return movieMapper.updateMovie(movie);
    }

    public List<Movie> selectAllMovies(Integer movieState){
        return movieMapper.selectAllMovies(movieState);
    }

    public List<Movie> selectMovieLikeName(String movieCName){
        return movieMapper.selectMovieLikeName(movieCName);
    }

    public List<Movie> selectMovieLikeType(String movieType){
        return movieMapper.selectMovieLikeType(movieType);
    }

    public List<Movie> sortMovieByDate(){
        return movieMapper.sortMovieByDate();
    }

    public List<Movie> sortMovieBycomentCount(){
        return movieMapper.sortMovieBycomentCount();
    }

    public List<Movie> selectMovieByBoxOffice(){
        return movieMapper.selectMovieByBoxOffice();
    }

    public List<Movie> sortMovieByScore(){
        return movieMapper.selectMovieByScore();
    }

    public Integer addMovieCommentCount(Integer id){
        return movieMapper.addMovieCommentCount(id);
    }

    public Integer reduceMovieCommentCount(Integer id){
        return movieMapper.reduceMovieCommentCount(id);
    }

    public Integer changeMovieBoxOffice(Float movieBoxOffice,Integer id){
        return movieMapper.changeMovieBoxOffice(movieBoxOffice,id);
    }

    public  Integer insertMoviePicture(String name,String moviePicture){
        return movieMapper.insertMoviePicture(name,moviePicture);
    }

    public Integer updateMoviePicture(Integer id,String moviePicture){
        return movieMapper.updateMoviePicture(id,moviePicture);
    }
}
