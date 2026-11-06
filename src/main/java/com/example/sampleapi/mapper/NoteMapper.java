package com.example.sampleapi.mapper;

import com.example.sampleapi.dto.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT id, title, content, created_at FROM notes ORDER BY created_at DESC")
    List<Note> findAll();

    @Insert("INSERT INTO notes (title,content) VALUES (#{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id,createdAt" , keyColumn = "id,createdAt")
    int insert(Note note);

    @Select("SELECT id,title,content,created_at FROM notes WHERE id = #{id}")
    Note findById(Long id);

    @Delete("DELETE FROM notes WHERE id = #{id}")
    int delete(long id);

    @Update("UPDATE notes SET title = #{title}, content = #{content} WHERE id = #{id}")
    int update(Note note);
}
