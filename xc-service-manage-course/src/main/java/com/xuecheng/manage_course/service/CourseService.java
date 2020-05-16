package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Autowired
    CourseMarketRepository courseMarketRepository;

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        TeachplanNode teachplanNode=teachplanMapper.selectList(courseId);
        return teachplanNode;
    }
    @Transactional
    public ResponseResult addTeachplan(Teachplan teachplan) {

        if(teachplan == null ||
                StringUtils.isEmpty(teachplan.getPname()) ||
                StringUtils.isEmpty(teachplan.getCourseid())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //课程id
        String courseid = teachplan.getCourseid();
        //父结点的id
        String parentid = teachplan.getParentid();
        if(StringUtils.isEmpty(parentid)){
            //获取课程的根结点
            parentid = getTeachplanRoot(courseid);
        }
        //查询根结点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        Teachplan teachplan1 = optional.get();
        //父结点的级别
        String parent_grade = teachplan1.getGrade();
        //创建一个新结点准备添加
        Teachplan teachplanNew = new Teachplan();
        //将teachplan的属性拷贝到teachplanNew中
        BeanUtils.copyProperties(teachplan,teachplanNew);
        //要设置必要的属性
        teachplanNew.setParentid(parentid);
        if(parent_grade.equals("1")){
            teachplanNew.setGrade("2");
        }else{
            teachplanNew.setGrade("3");
        }
        teachplanNew.setStatus("0");//未发布
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //获取课程的根结点
    public String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        CourseBase courseBase = optional.get();
        //调用dao查询teachplan表得到该课程的根结点（一级结点）
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");
        if(teachplanList == null || teachplanList.size()<=0){
            //新添加一个课程的根结点
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);
            teachplan.setParentid("0");
            //一级结点
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplan.setPname(courseBase.getName());
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        //返回根结点的id
        return teachplanList.get(0).getId();
    }

    public CourseBase getCourseBaseById(String courseid){
        Optional<CourseBase> optional=courseBaseRepository.findById(courseid);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }


    @Transactional
    public ResponseResult updateCourseBase(String courseId,CourseBase courseBase){
        CourseBase courseBase1=this.getCourseBaseById(courseId);
        if(courseBase1==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        courseBase1.setName(courseBase.getName());
        courseBase1.setMt(courseBase.getMt());
        courseBase1.setSt(courseBase.getSt());
        courseBase1.setGrade(courseBase.getGrade());
        courseBase1.setStudymodel(courseBase.getStudymodel());
        courseBase1.setUsers(courseBase.getUsers());
        courseBase1.setDescription(courseBase.getDescription());

        CourseBase save=courseBaseRepository.save(courseBase1);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public CourseMarket getCourseMarketById(String courseid){
        Optional<CourseMarket> optional=courseMarketRepository.findById(courseid);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Transactional
    public CourseMarket updateCourseMarket(String id,CourseMarket courseMarket){
        CourseMarket one=this.getCourseMarketById(id);
        if(one==null){
            //添加课程营销信息
            one = new CourseMarket();
            BeanUtils.copyProperties(courseMarket, one);
            //设置课程 id
            one.setId(id);
            courseMarketRepository.save(one);
        }else{
            one.setCharge(courseMarket.getCharge());
            one.setStartTime(courseMarket.getStartTime());
            //课程有效期，开始时间
            one.setEndTime(courseMarket.getEndTime());
            // 课程有效期，结束时间
            one.setPrice(courseMarket.getPrice());
            one.setQq(courseMarket.getQq());
            one.setValid(courseMarket.getValid());
            courseMarketRepository.save(one);
        }
        return one;

    }



}
