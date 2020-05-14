package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;

public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeanchplanList(String courseId);

}
