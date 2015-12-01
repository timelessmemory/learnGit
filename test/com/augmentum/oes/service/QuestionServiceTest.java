package com.augmentum.oes.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.service.QuestionService;
import com.augmentum.onlineexamsystem.util.Pagination;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:oes-mvc.xml"})
public class QuestionServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    private QuestionService questionService;

    @Before
    public void setUp() throws Exception {
        questionService = (QuestionService) this.applicationContext.getBean("questionService");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateQuestion() {
        //test normal data
        Question question = new Question();
        question.setTitle("Which country do you like?");
        question.setAnswerA("Which country do you like?");
        question.setAnswerB("Which country do you like?");
        question.setAnswerC("Which country do you like?");
        question.setAnswerD("Which country do you like?");
        question.setCorrectAnswer("A");

//test empty string
//        question.setTitle("");
//        question.setAnswerA("");
//        question.setAnswerB("");
//        question.setAnswerC("");
//        question.setAnswerD("");
//        question.setCorrectAnswer("");

//test too long string
//        question.setTitle("Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?"
//                + "Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?"
//                + "Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?"
//                + "Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?"
//                + "Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?");
//        question.setAnswerA("Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?");
//        question.setAnswerB("Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?");
//        question.setAnswerC("Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?");
//        question.setAnswerD("Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?Which country do you like?");
//        question.setCorrectAnswer("");

        try {
            int id = questionService.createQuestion(question);
            Question dbQuestion = questionService.getQuestionByQuestionId(id);
            //watch data after insert whether consistent of origin data
            System.out.println(dbQuestion);
        } catch (ParameterException pe) {
            Map<String, String> errorMap = pe.getErrors();
            Set<Entry<String, String>> entrySet = errorMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
                System.out.println("key: " + entry.getKey());
                System.out.println("value: " + entry.getValue());
            }
        }

    }

    @Test
    public void testFindQuestions() {
        //test normal data
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(2);
        pagination.setPageSize(15);

//test current page less than 0
//        Pagination pagination = new Pagination();
//        pagination.setCurrentPage(-8);
//        pagination.setPageSize(10);

//test pageSize less than 0
//        Pagination pagination = new Pagination();
//        pagination.setCurrentPage(8);
//        pagination.setPageSize(-10);

//test current page greater than total size
//        Pagination pagination = new Pagination();
//        pagination.setCurrentPage(900000000);
//        pagination.setPageSize(10);

//test page size greater question total count
//        Pagination pagination = new Pagination();
//        pagination.setCurrentPage(5);
//        pagination.setPageSize(1000000);

        List<Question> questionList = questionService.findQuestions("ASC", "", pagination);
//        List<Question> questionList = questionService.findQuestions("ASC", "#", pagination);
        for (Question value : questionList) {
            System.out.println(value);
        }

    }
    @Test

    public void testGetQuestionByQuestionId() {
//test not exist data
        Question dbQuestion = questionService.getQuestionByQuestionId(1);

//test normal data
//        Question dbQuestion = questionService.getQuestionByQuestionId(45253);

// test less than 0
//        Question dbQuestion = questionService.getQuestionByQuestionId(-3);

// test greater than largest data
//        Question dbQuestion = questionService.getQuestionByQuestionId(1000000000);
        System.out.println(dbQuestion);

    }

    @Test
    public void testUpdateQuestionByQuestionId() {
        //test normal data
        Question question = questionService.getQuestionByQuestionId(45262);
        question.setTitle("Which city do you want to live in ?");

        //test null data
//        Question question = new Question();
//        question.setId(45263);
        try {
            questionService.updateQuestionByQuestionId(question);
            System.out.println(questionService.getQuestionByQuestionId(45262));
        } catch (ParameterException pe) {
            Map<String, String> errorMap = pe.getErrors();
            Set<Entry<String, String>> entrySet = errorMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
                System.out.println("key: " + entry.getKey());
                System.out.println("value: " + entry.getValue());
            }
        }
    }

    @Test
    public void testDeleteQuestionByQuestionId() {
        //test normal data
        questionService.deleteQuestionByQuestionId(45266);
        Assert.assertNull(questionService.getQuestionByQuestionId(45266));

        //test not exist data
//        questionService.deleteQuestionByQuestionId(1);
    }

    @Test //logic delete
    public void testDeleteQuestionsByQuestionId() {
        int idInts[] = new int[]{45267, 45268, 45269};
        questionService.deleteQuestionsByQuestionId(idInts);
        Assert.assertNull(questionService.getQuestionByQuestionId(45267));
        Assert.assertNull(questionService.getQuestionByQuestionId(45268));
        Assert.assertNull(questionService.getQuestionByQuestionId(45269));

        //test not exist data
//      int idns[] = new int[]{1,2,3};
//      questionService.deleteQuestionsByQuestionId(idns);

//test empty
//        int idns[] = new int[]{};
//        questionService.deleteQuestionsByQuestionId(idns);
    }

    @Test
    public void testQuestionTitleValidation() {
//test exist
        boolean result = questionService.questionTitleValidation("as");
        Assert.assertEquals(true, result);

//test not exist
//        boolean rs = questionService.questionTitleValidation("Do you like fruit?");
//        Assert.assertEquals(false, rs);

//test empty string
//        boolean rs = questionService.questionTitleValidation("");
//        Assert.assertEquals(false, rs);
    }

}
