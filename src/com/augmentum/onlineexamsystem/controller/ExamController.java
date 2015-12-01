package com.augmentum.onlineexamsystem.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.model.Result;
import com.augmentum.onlineexamsystem.service.ExamService;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.DBUtil;
import com.augmentum.onlineexamsystem.util.Pagination;
import com.augmentum.onlineexamsystem.util.SessionUtil;
import com.augmentum.onlineexamsystem.util.StringUtils;
@Controller
public class ExamController extends BaseController {
    @Resource
    private ExamService examService;
    private static final String SHOW_EXAM_CREATE = "exam/exam-create";
    private static final String PARAMTER_ERROR = "error/parameter-error.jsp";
    private static final String  SHOW_EXAM = "exam/exam-list";
    private static final String  EXAM_DETAIL = "exam/exam-detail";
    private static final String  EXAM_EDIT = "exam/exam-edit";
    private static final String  TIME_FRAGMENT = ":00";
    private static final String  REPLACE_T = "T";
    private static final String  EMPTY_SPACE = " ";
    private static final String  DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String  NULL = "null";
    private static final String  ZERO = "0";
    private static final String  ONE = "1";
    private Logger logger = Logger.getLogger(QuestionController.class);

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }

    @RequestMapping(value = Constants.CREATE_EXAM, method = RequestMethod.GET)
    public ModelAndView createExam() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(SHOW_EXAM_CREATE);
        return modelAndView;
    }

    @RequestMapping(value = Constants.VALIDATION_IS_AS_DRAFT, method = RequestMethod.POST)
    @ResponseBody
    public Result validationIsAsDraft(@RequestParam(value = Constants.QUANTITY, defaultValue = Constants.EMPTY_STR) int quantity) {
        Result result = new Result();
        boolean flag = examService.isQuestionEnough(quantity);
        if (flag) {
            result.setResult(Constants.NO);
        } else {
            result.setResult(Constants.YES);
        }
        return result;
    }

    @RequestMapping(value = Constants.CREATE_EXAM, method = RequestMethod.POST)
    public ModelAndView createExamPost(@RequestParam(value = Constants.EFFECTIVE_TIME) String dateString,
                                       @RequestParam(value = Constants.DESCRIPTION) String description,
                                       @RequestParam(value = Constants.IS_DRAFT) String isDraft,
                                       @RequestParam(value = Constants.DURATION) String duration,
                                       @RequestParam(value = Constants.EXAM_NAME) String examName,
                                       @RequestParam(value = Constants.PASSCRITERIA) String passCriteria,
                                       @RequestParam(value = Constants.POINT) String point,
                                       @RequestParam(value = Constants.QUANTITY) String quantity,
                                       @RequestParam(value = Constants.TOTALSCORE) String totalScore) throws UnsupportedEncodingException{

        ModelAndView modelAndView = new ModelAndView();
        Exam exam = new Exam();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            dateString = dateString.replace(REPLACE_T, EMPTY_SPACE);
            Date date = sdf.parse(dateString + TIME_FRAGMENT);
            exam.setCreator((String)AppContext.getAppContext().getDataValue(Constants.USER));
            exam.setDescription(description);
            exam.setEffectiveTime(date);
            exam.setExamName(examName);
            exam.setDuration(Integer.valueOf(duration));
            exam.setPassCriteria(Integer.valueOf(passCriteria));
            exam.setQuestionPoints(Integer.valueOf(point));
            exam.setQuestionQuantity(Integer.valueOf(quantity));
            exam.setTotalScore(Integer.valueOf(totalScore));
        } catch (Exception e) {
            logger.error(e);
            modelAndView.setView(new RedirectView(AppContext.getContextPath()  + Constants.EXCEPTION + Constants.QUES_CHAR + Constants.FORWARD_PAGE + Constants.QUES_CHAR + PARAMTER_ERROR));
            return modelAndView;
        }

        try {//0 represent the required question enough 1 represent the question is not enough, as a draft
            if (isDraft.equals(ZERO)) {
                examService.createExam(exam);
                SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.CREATE_EXAM_SUC);
            } else if (isDraft.equals(ONE)) {
                examService.createExamAsDraft(exam);
                SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.CREATE_EXAM_AS_DRAFT);
            } else {
                modelAndView.setView(new RedirectView(AppContext.getContextPath()  + Constants.EXCEPTION + Constants.QUES_CHAR + Constants.FORWARD_PAGE + Constants.EQUAL_CHAR + PARAMTER_ERROR));
                return modelAndView;
            }
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_EXAM_SERVLET));
            return modelAndView;
        } catch (ParameterException pe) {
            logger.error(pe);
            SessionUtil.addSessionAttr(Constants.TIP_MESSAGE, pe.getErrors());
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.CREATE_EXAM));
            return modelAndView;
        }
    }

    @RequestMapping(value = Constants.SHOW_EXAM_SERVLET, method = RequestMethod.GET)
    public ModelAndView showExam(@RequestParam(value = Constants.ORDER_STR, defaultValue = Constants.EMPTY_STR) String orderStr,
                                 @RequestParam(value = Constants.COLUMN_STR, defaultValue = Constants.EMPTY_STR) String columnStr,
                                 @RequestParam(value = Constants.NAME_CONDITION, defaultValue = Constants.EMPTY_STR) String nameCondition,
                                 @RequestParam(value = Constants.FROM_DATE, defaultValue = Constants.EMPTY_STR) String fromDate,
                                 @RequestParam(value = Constants.TO_DATE, defaultValue = Constants.EMPTY_STR) String toDate,
                                 @RequestParam(value = Constants.CUR_PAGE, defaultValue = Constants.EMPTY_STR) String currentPage,
                                 @RequestParam(value = Constants.PER_PAGE, defaultValue = Constants.EMPTY_STR) String perPage
                                 ) throws UnsupportedEncodingException {
        nameCondition = StringUtils.convertCoding(nameCondition);
        columnStr = StringUtils.convertCoding(columnStr);
        String condition = DBUtil.convertSpecialCharacter(nameCondition);

        String[] orderArray = orderStr.split(Constants.CAMMA); //exception
        String[] columnArray = columnStr.split(Constants.CAMMA); // not equals columnName Sort throw exception

        Map<String, String> map = new LinkedHashMap<String, String>();

        if (orderArray[0].equals(Constants.EMPTY_STR)) {
            orderArray = new String[3];
            columnArray = new String[3];
            orderArray[0] = Constants.SORT_ASC;
            columnArray[0] = Constants.COLUMN_ID;
            orderArray[1] = Constants.SORT_ASC;
            columnArray[1] = Constants.COLUMN_EXAM_NAME;
            orderArray[2] = Constants.SORT_ASC;
            columnArray[2] = Constants.COLUMN_EFFECTIVE_TIME;
            map.put(Constants.COLUMN_ID, Constants.SORT_ASC);
            map.put(Constants.COLUMN_EXAM_NAME, Constants.SORT_ASC);
            map.put(Constants.COLUMN_EFFECTIVE_TIME, Constants.SORT_ASC);
        } else {
            map.put(columnArray[0], orderArray[0]);
            map.put(columnArray[1], orderArray[1]);
            map.put(columnArray[2], orderArray[2]);
        }


        Pagination pagination = new Pagination();
        Pagination.pageconvert(currentPage, pagination, logger, perPage);

        ModelAndView modelAndView = new ModelAndView();
        if (nameCondition.equals(Constants.EMPTY_STR)) {
            nameCondition = null;
        } else {
            modelAndView.addObject(Constants.NAME_CONDITION, nameCondition);
        }
        if (fromDate.equals(Constants.EMPTY_STR) || fromDate.equals(NULL)) {
            fromDate = null;
        } else {
            modelAndView.addObject(Constants.FROM_DATE, fromDate);
        }
        if (toDate.equals(Constants.EMPTY_STR) || toDate.equals(NULL)) {
            toDate = null;
        } else {
            modelAndView.addObject(Constants.TO_DATE, toDate);
        }
        if (condition.equals(Constants.EMPTY_STR)) {
            condition = null;
        }
        List<Exam> exams = examService.findExams(orderArray[0], orderArray[1], orderArray[2], columnArray[0], columnArray[1], columnArray[2], condition, fromDate, toDate, pagination);

        modelAndView.addObject(Constants.EXAM, exams);
        modelAndView.addObject(Constants.ORDER_MAP, map);
        modelAndView.addObject(Constants.PAGINATION, pagination);
        modelAndView.setViewName(SHOW_EXAM);
        return modelAndView;
    }

    @RequestMapping(value = Constants.SHOW_EXAM_DETAIL, method = RequestMethod.GET)
    public ModelAndView showExamDetailById(@RequestParam(value = Constants.COLUMN_ID, defaultValue = Constants.EMPTY_STR) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.showExamDetailById(id);
        modelAndView.addObject(Constants.EXAM, exam);
        modelAndView.setViewName(EXAM_DETAIL);
        return modelAndView;
    }

    @RequestMapping(value = Constants.EDIT_EXAM_SERVLET, method = RequestMethod.GET)
    public ModelAndView editExamById(@RequestParam(value = Constants.COLUMN_ID) int id) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.showExamDetailById(id);
        modelAndView.addObject(Constants.EXAM, exam);
        modelAndView.setViewName(EXAM_EDIT);
        return modelAndView;
    }

    @RequestMapping(value = Constants.EDIT_EXAM_SERVLET, method = RequestMethod.POST)
    public ModelAndView editExamPost(Exam exam, @RequestParam(value = Constants.EFFECTIVE_EXAM_TIME, defaultValue = Constants.EMPTY_STR) String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        dateString = dateString.replace(REPLACE_T, EMPTY_SPACE);
        Date date = sdf.parse(dateString + TIME_FRAGMENT);
        exam.setEffectiveTime(date);
        ModelAndView modelAndView = new ModelAndView();
        int result = examService.editExam(exam);
        if (result == 0) {
            modelAndView.addObject(Constants.EXAM, examService.showExamDetailById(exam.getId()));
            modelAndView.setViewName(EXAM_DETAIL);
        } else {
            SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.UPDATE_EXAM_FAIL);
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_EXAM_SERVLET));
        }
        return modelAndView;
    }

    @RequestMapping(value = Constants.DELETE_EXAM_BY_ID, method = RequestMethod.GET)
    public ModelAndView deleteExam(@RequestParam(value = Constants.COLUMN_ID, defaultValue = Constants.EMPTY_STR) String id) {
        ModelAndView modelAndView = new ModelAndView();
        if (!StringUtils.isEmpty(id)) {
            String idStrs[] = id.split(Constants.CAMMA);
            if (idStrs.length  == 1 ) {
                try {
                    examService.deleteExamById(Integer.valueOf(id));
                } catch (NumberFormatException e) {
                    logger.error(e);
                }
            } else {
                int[] idInts = StringUtils.toIntArray(idStrs);
                examService.deleteExamsById(idInts);
            }
        }
        SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.DELETE_EXAM_SUC_MESSAGE);
        modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_EXAM_SERVLET));
        return modelAndView;
    }
}
