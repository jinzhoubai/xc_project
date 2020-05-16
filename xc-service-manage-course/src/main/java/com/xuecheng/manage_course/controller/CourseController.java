package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    //查询课程计划
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeanchplanList(@PathVariable("courseId")String courseId){
        return courseService.findTeachplanList(courseId);
    }

    //添加课程
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/coursebase/get/{courseId}")
    public CourseBase findCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseBaseById(courseId);
    }

    @Override
    @PutMapping("/coursebase/update/{id}")
    public ResponseResult updateCourseBase(@PathVariable("id") String courseId, @RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(courseId,courseBase);
    }

    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CourseMarket findCourseMarketById(@PathVariable("courseId")String courseId) {
        return  courseService.getCourseMarketById(courseId);
    }

    @Override
    @PostMapping("/coursemarket/update/{id}")
    public ResponseResult updateCourseMarket(@PathVariable("id")String couresId, @RequestBody CourseMarket courseMarket) {
        CourseMarket courseMarket_u = courseService.updateCourseMarket(couresId, courseMarket);
        if(courseMarket_u!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            return new ResponseResult(CommonCode.FAIL);
        }
    }
}
