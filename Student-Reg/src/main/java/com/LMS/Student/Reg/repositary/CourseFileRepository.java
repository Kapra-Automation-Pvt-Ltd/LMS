package com.LMS.Student.Reg.repositary;

import com.LMS.Student.Reg.entity.CourseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseFileRepository extends JpaRepository<CourseFile,Long> {

    @Query("select  f from CourseFile as f where f.course.id = ?1")
    List<CourseFile> findFilesByCourseId(Long courseId);

    @Modifying
    @Query("delete from CourseFile as f where f.course.id = ?1 and f.modifiedFileName in (?2 )")
    void deleteFilesByUserIdAndImageNames(Long courseId, List<String> removeImages);

    @Modifying
    @Query("delete from CourseFile as f where f.course.id = ?1 ")
    void deleteFilesByCourseId(Long courseId);
}
