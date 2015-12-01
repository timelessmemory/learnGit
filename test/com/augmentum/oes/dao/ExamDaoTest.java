package com.augmentum.oes.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.augmentum.onlineexamsystem.dao.ExamDao;
import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.util.Pagination;

public class ExamDaoTest {
    private ExamDao examDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        examDao = (ExamDao) applicationContext.getBean("examDao");
    }

    @Test
    public void testCreateExam() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse("2015-08-01 10:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Exam exam = new Exam("english", "easy", date, 60, 5, 20, 100, 60, "Mario");
        examDao.createExam(exam);
    }

    @Test
    public void testListExam() {
        int count = examDao.getTotalCountByCondition(null, "2015-07-09", null);
        System.out.println(count);
    }

    @Test
    public void testfindExams() {
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setPageSize(8);
        List<Exam> exmalsExams = examDao.findExams("ASC", "ASC", "ASC", "question_quantity", "duration", "total_score", null, null, null, pagination);
        for(Exam exam : exmalsExams) {
            System.out.println(exam);
        }
    }

}
