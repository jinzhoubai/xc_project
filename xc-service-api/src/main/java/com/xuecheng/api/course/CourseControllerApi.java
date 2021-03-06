package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="课程管理接口",description = "课程管理接口，提供课程的增、删、改、查")
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeanchplanList(String courseId);
    @ApiOperation("添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("获取课程的基本信息")
    public CourseBase findCourseBaseById(String courseId);

    @ApiOperation("更新课程的基本信息")
    public ResponseResult updateCourseBase(String courseId,CourseBase courseBase);

    @ApiOperation("查询课程营销信息")
    public CourseMarket findCourseMarketById(String courseId);

    @ApiOperation("更新课程的营销信息")
    public ResponseResult updateCourseMarket(String couresId,CourseMarket courseMarket);


}
