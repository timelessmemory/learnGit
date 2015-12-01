package com.augmentum.onlineexamsystem.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.augmentum.onlineexamsystem.common.AppContext;
import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.model.QuestionResult;
import com.augmentum.onlineexamsystem.model.Result;
import com.augmentum.onlineexamsystem.service.QuestionService;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.DBUtil;
import com.augmentum.onlineexamsystem.util.Pagination;
import com.augmentum.onlineexamsystem.util.SessionUtil;
import com.augmentum.onlineexamsystem.util.StringUtils;
@Controller
public class QuestionController extends BaseController {
    @Resource
    private QuestionService questionService;
    private Logger logger = Logger.getLogger(QuestionController.class);
    private static final String  PARAM_ERROR = "parameter error!";
    private static final String SHOW_QUESTION_LIST = "question-list";
    private static final String SHOW_QUESTION_CREATE = "question-create";
    private static final String PARAMTER_ERROR = "error/parameter-error.jsp";
    private static final String AJAX_TITLE = "ajaxtitle";
    private static final String QUESTION_LIST = "question-edit";
    private static final String  SHOW_DETAIL = "question-detail";
    private static final String EXIST = "exist";
    private static final String UNEXIST = "unexist";
    private static final String  AND = "&";

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = Constants.CREATE_QUESTION, method = RequestMethod.GET)
    public ModelAndView createQuestion() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(SHOW_QUESTION_CREATE);
        return modelAndView;
    }

    @RequestMapping(value = Constants.VALIDATION_QUESTION, method = RequestMethod.POST)
    @ResponseBody
    public Result validationQuestionTitle(@RequestParam(value = AJAX_TITLE, defaultValue = Constants.EMPTY_STR) String ajaxTitle) throws UnsupportedEncodingException {
        Result result = new Result();
        if(!StringUtils.isEmpty(ajaxTitle)) {
            ajaxTitle = new String(ajaxTitle.getBytes(Constants.ISO), Constants.UTF);
            boolean flag = questionService.questionTitleValidation(ajaxTitle);
            if (flag) {
                result.setResult(EXIST);
            } else {
                result.setResult(UNEXIST);
            }
        }
        return result;
    }

    @RequestMapping(value = Constants.CREATE_QUESTION, method = RequestMethod.POST)
    public ModelAndView createQuestionPost(@RequestParam(value = Constants.QUESTION_TITLE, defaultValue =  Constants.EMPTY_STR) String title,
                                           @RequestParam(value = Constants.QUESTION_ANSWER_A, defaultValue = Constants.EMPTY_STR) String answerA,
                                           @RequestParam(value = Constants.QUESTION_ANSWER_B, defaultValue = Constants.EMPTY_STR) String answerB,
                                           @RequestParam(value = Constants.QUESTION_ANSWER_C, defaultValue = Constants.EMPTY_STR) String answerC,
                                           @RequestParam(value = Constants.QUESTION_ANSWER_D, defaultValue = Constants.EMPTY_STR) String answerD,
                                           @RequestParam(value = Constants.QUESTION_OPTION, defaultValue = Constants.EMPTY_STR) String correctAnswer) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = new Question();
        question.setTitle(title);
        question.setAnswerA(answerA);
        question.setAnswerB(answerB);
        question.setAnswerC(answerC);
        question.setAnswerD(answerD);
        question.setCorrectAnswer(correctAnswer);

        try {
            questionService.createQuestion(question);
            SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.CREATE_QUESTION_SUC_MESSAGE);
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.LOGIN_SUC_SERVLET));
        } catch (ParameterException pe) {
            logger.error(pe.getMessage(), pe);
            SessionUtil.addSessionAttr(Constants.TIP_MESSAGE, pe.getErrors());
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.CREATE_QUESTION));
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping(value = Constants.DELETE_QUESTION_SERVLET, method = RequestMethod.GET)
    public ModelAndView deleteQuestion(@RequestParam(value = Constants.ID, defaultValue = Constants.EMPTY_STR) String id,
                                       @RequestParam(value = Constants.CUR_PAGE, defaultValue = Constants.EMPTY_STR) String currentPage,
                                       @RequestParam(value = Constants.QUESTION_QUERY_CONDITION, defaultValue = Constants.EMPTY_STR) String originCondtion,
                                       @RequestParam(value = Constants.ORDER_FLAG, defaultValue = Constants.EMPTY_STR) String order,
                                       @RequestParam(value = Constants.PER_PAGE, defaultValue = Constants.EMPTY_STR) String perPage) {
        ModelAndView modelAndView = new ModelAndView();
        if (!StringUtils.isEmpty(id)) {
            String idStrs[] = id.split(Constants.CAMMA);
            try {
                if (idStrs.length  == 1 ) {
                        questionService.deleteQuestionByQuestionId(Integer.valueOf(id));
                } else {
                    int[] idInts = StringUtils.toIntArray(idStrs);
                    questionService.deleteQuestionsByQuestionId(idInts);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                logger.error(PARAM_ERROR);
                modelAndView.setView(new RedirectView(AppContext.getContextPath()  + Constants.EXCEPTION + Constants.QUES_CHAR + Constants.FORWARD_PAGE + Constants.EQUAL_CHAR + PARAMTER_ERROR));
                return modelAndView;
            }
        }

        SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.DELETE_QUESTION_SUC_MESSAGE);
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.QUES_CHAR)
          .append(Constants.CUR_PAGE)
          .append(Constants.EQUAL_CHAR)
          .append(currentPage)
          .append(AND)
          .append(Constants.QUESTION_QUERY_CONDITION)
          .append(Constants.EQUAL_CHAR)
          .append(originCondtion)
          .append(AND)
          .append(Constants.ORDER_FLAG)
          .append(Constants.EQUAL_CHAR)
          .append(order)
          .append(AND)
          .append(Constants.PER_PAGE)
          .append(Constants.EQUAL_CHAR)
          .append(perPage);
        modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_QUESTION_SERVLET + sb.toString()));
        return modelAndView;
    }

    @RequestMapping(value = Constants.AJAX_DELETE_QUESTION_SERVLET, method = RequestMethod.GET)
    @ResponseBody
    public Result ajaxDeleteQuestion(@RequestParam(value = "items") String id) {
        Result result = new Result();
        if (!StringUtils.isEmpty(id)) {
            String idStrs[] = id.split(Constants.CAMMA);
            try {
                if (idStrs.length  == 1 ) {
                    questionService.deleteQuestionByQuestionId(Integer.valueOf(id));
                } else {
                    int[] idInts = StringUtils.toIntArray(idStrs);
                    questionService.deleteQuestionsByQuestionId(idInts);
                }
                result.setResult(Constants.DELETE_QUESTION_SUC_MESSAGE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                logger.error(PARAM_ERROR);
                result.setResult(PARAM_ERROR);
            }
        }
        return result;
    }

    @RequestMapping(value = Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET, method = RequestMethod.GET)
    public ModelAndView editQuestion(@RequestParam(value = Constants.ID, defaultValue = Constants.EMPTY_STR) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.getQuestionByQuestionId(id);
        modelAndView.addObject(Constants.QUESTION, question);
        modelAndView.setViewName(QUESTION_LIST);
        return modelAndView;
    }

    @RequestMapping(value = Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET, method = RequestMethod.POST)
    public ModelAndView editQuestionPost(@RequestParam(value = Constants.QUESTION_TITLE, defaultValue = Constants.EMPTY_STR) String title,
                                         @RequestParam(value = Constants.QUESTION_ANSWER_A, defaultValue = Constants.EMPTY_STR) String answerA,
                                         @RequestParam(value = Constants.QUESTION_ANSWER_B, defaultValue = Constants.EMPTY_STR) String answerB,
                                         @RequestParam(value = Constants.QUESTION_ANSWER_C, defaultValue = Constants.EMPTY_STR) String answerC,
                                         @RequestParam(value = Constants.QUESTION_ANSWER_D, defaultValue = Constants.EMPTY_STR) String answerD,
                                         @RequestParam(value = Constants.QUESTION_OPTION, defaultValue = Constants.EMPTY_STR) String correctAnswer,
                                         @RequestParam(value = Constants.ID, defaultValue = Constants.EMPTY_STR) String id) {
        Question question = new Question();
        question.setId(Integer.valueOf(id));
        question.setTitle(title);
        question.setAnswerA(answerA);
        question.setAnswerB(answerB);
        question.setAnswerC(answerC);
        question.setAnswerD(answerD);
        question.setCorrectAnswer(correctAnswer);

        ModelAndView modelAndView = new ModelAndView();
        try {
            questionService.updateQuestionByQuestionId(question);
            SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.UPDATE_QUESTION_SUC_MESSAGE);
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.LOGIN_SUC_SERVLET));
        } catch (ParameterException pe) {
            logger.error(pe.getMessage(), pe);
            SessionUtil.addSessionAttr(Constants.TIP_MESSAGE, pe.getErrors());
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET));
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping(value = Constants.SHOW_QUESTION_DETAIL, method = RequestMethod.GET)
    public ModelAndView showQuestionDetail(@RequestParam(value = Constants.ID) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Question question = questionService.getQuestionByQuestionId(id);
        modelAndView.addObject(Constants.QUESTION, question);
        modelAndView.setViewName(SHOW_DETAIL);
        return modelAndView;
    }

    @RequestMapping(value = Constants.SHOW_QUESTION_SERVLET, method = RequestMethod.GET)
    public ModelAndView showQuestion(@RequestParam(value = Constants.QUESTION_QUERY_CONDITION, defaultValue = Constants.EMPTY_STR) String originCondtion,
                                     @RequestParam(value = Constants.ORDER_FLAG, defaultValue = Constants.EMPTY_STR) String order,
                                     @RequestParam(value = Constants.CUR_PAGE, defaultValue = Constants.EMPTY_STR) String currentPage,
                                     @RequestParam(value = Constants.PER_PAGE, defaultValue = Constants.EMPTY_STR) String perPage) throws UnsupportedEncodingException {
        if (!StringUtils.isEmpty(originCondtion)) {
            originCondtion = new String(originCondtion.getBytes(Constants.ISO), Constants.UTF);
        }
        String condition = DBUtil.convertSpecialCharacter(originCondtion);

        if (StringUtils.isEmpty(order)) {
            order = Constants.ORDER_ASC;
        }

        Pagination pagination = new Pagination();
        Pagination.pageconvert(currentPage, pagination, logger, perPage);

        List<Question> questions = questionService.findQuestions(order, condition,pagination);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constants.QUESTION, questions);

        if (StringUtils.isEmpty(order)) {
            modelAndView.addObject(Constants.ORDER_FLAG, Constants.ORDER_ASC);
        } else {
            if (Constants.ORDER_DSC.equals(order)) {
                modelAndView.addObject(Constants.ORDER_FLAG, Constants.ORDER_DSC);
            } else {
                modelAndView.addObject(Constants.ORDER_FLAG, Constants.ORDER_ASC);
            }
        }
        modelAndView.addObject(Constants.PAGINATION, pagination);
        modelAndView.addObject(Constants.QUESTION_QUERY_CONDITION, originCondtion);
        modelAndView.addObject(Constants.IS_QUESTION_SEARCH, true);
        modelAndView.setViewName(SHOW_QUESTION_LIST);
        return modelAndView;
    }

    @RequestMapping(value = Constants.SHOW_QUESTION_AJAX, method = RequestMethod.POST)
    @ResponseBody
    public QuestionResult showQuestionAjax(@RequestParam(value = Constants.QUESTION_QUERY_CONDITION, defaultValue = Constants.EMPTY_STR) String originCondtion,
                                         @RequestParam(value = Constants.ORDER_FLAG, defaultValue = Constants.EMPTY_STR) String order,
                                         @RequestParam(value = Constants.CUR_PAGE, defaultValue = Constants.EMPTY_STR) String currentPage,
                                         @RequestParam(value = Constants.PER_PAGE, defaultValue = Constants.EMPTY_STR) String perPage) throws UnsupportedEncodingException {

        String condition = DBUtil.convertSpecialCharacter(originCondtion);

        if (StringUtils.isEmpty(order)) {
            order = Constants.ORDER_ASC;
        }

        Pagination pagination = new Pagination();
        Pagination.pageconvert(currentPage, pagination, logger, perPage);

        List<Question> questions = questionService.findQuestions(order, condition,pagination);

        QuestionResult questionResult = new QuestionResult();
        questionResult.setQuestionList(questions);


        if (Constants.ORDER_DSC.equals(order)) {
            questionResult.setOrderflag(Constants.ORDER_DSC);
        } else {
            questionResult.setOrderflag(Constants.ORDER_ASC);
        }
        questionResult.setPagination(pagination);
        questionResult.setQuestionCondition(originCondtion);
        questionResult.setQuestionSearch(true);
        return questionResult;
    }
}
