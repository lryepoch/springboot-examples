package com.annotation.service.impl;

import com.annotation.entity.Student;
import com.annotation.mapper.StudentMapper;
import com.annotation.service.StudentService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @description 使用redis注解
* @author lryepoch
* @date 2020/9/5 15:17
*
*/
//指定缓存名称，对应配置文件中 spring.cache.cache-names=cache-user
@CacheConfig(cacheNames = "oneMin")       //此处添加这个，具体到方法中就不需要声明cacheNames了
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Override
    @Cacheable(/*cacheNames = "student",*/ key = "#id",unless="#result == null")  //用于查询
    public Student selectStudentById(Integer id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    @CacheEvict(/*cacheNames = "student",*/ key = "#id")  //用于删除
    public int deleteStudent(Integer id) {
        return studentMapper.deleteStudent(id);
    }

    @Override
    @CachePut(/*cacheNames = "student",*/ key = "#student.id")  //用于更新
    public Student updateStudent(Student student) {
        studentMapper.updateStudent(student);
        return studentMapper.selectStudentById(student.getId());
    }

    /**
    * @description 新增不用缓存
    * @author lryepoch
    * @date 2020/11/26 18:31
    *
    */
    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }


    @Cacheable(value = "allStudentsCache", unless = "#result.size() == 0")  //value替换了全局cacheNames
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    /**
     * 创建用户，同时使用新的返回值的替换缓存中的值
     * 创建用户后会将allUsersCache缓存全部清空
     * @return
     */
    @Caching(
            put = {@CachePut(/*value = "student", */key = "#student.id")},
            evict = {@CacheEvict(value = "allStudentsCache", allEntries = true)}
    )
    @Override
    public Student addStudent1(Student student) {
        studentMapper.addStudent(student);
        return student;
    }

    /**
     * 更新用户，同时使用新的返回值的替换缓存中的值
     * 更新用户后会将allUsersCache缓存全部清空
     */
    @Caching(
            put = {@CachePut(/*value = "student",*/ key = "#student.id")},
            evict = {@CacheEvict(value = "allStudentsCache", allEntries = true)}
    )
    @Override
    public Student updateStudent1(Student student) {
        studentMapper.updateStudent(student);
        return studentMapper.selectStudentById(student.getId());
    }

    /**
     * 对符合key条件的记录从缓存中移除
     * 删除用户后会将allUsersCache缓存全部清空
     */
    @Caching(
            evict = {
                    @CacheEvict(/*value = "student",*/ key = "#id"),
                    @CacheEvict(value = "allStudentsCache", allEntries = true)
            }
    )
    @Override
    public int deleteStudent1(Integer id) {
        return studentMapper.deleteStudent(id);
    }

}
