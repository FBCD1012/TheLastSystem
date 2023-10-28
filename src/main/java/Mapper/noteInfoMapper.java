package Mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;

//传入
public interface noteInfoMapper {
    @Insert("INSERT INTO noteInfo (noteId,noteName,notedTime,noter,authorId) values(#{noteId},#{noteName},#{notedTime},#{author},#{authorId})")
    int InsertNoteInfo(@Param("noteId")Integer noteId, @Param("noteName")String noteName,
                       @Param("notedTime") Date time,@Param("author")String noter,
                       @Param("authorId")Integer authorId);

    @Select("SELECT * FROM noteInfo")
    noteInfoMapper getNoteInfo();

    //获取相关的名称操作自动进行插入
    @Select("SELECT DISTINCT authorId FROM noteInfo where noter=#{noteName}")
    Integer getNoteName(@Param("noteName")String noteName);

    @Select("SELECT COUNT(*) FROM noteInfo")
    int getCountInt();
}
