package cinema.demo.dao;

import cinema.demo.entity.Schedule;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    Schedule selectScheduleById(Integer id);

    PageInfo<Schedule> selectScheduleByState(@Param("page")Integer page, @Param("limit")Integer limit, @Param("scheduleState")Integer scheduleState);

    Integer delScheduleRemain(Integer scheduleId);

    List<Schedule> selectAllSchedule();

    List<Schedule> selectScheduleByMovieName(String movieCName);

    List<Schedule> selectOffScheduleByMovieName(String movieCName);

    Integer insertSchedule(Schedule schedule);
}
