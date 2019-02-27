package info.hanniu.hanniupk.backend.modular.service.impl;

import info.hanniu.hanniupk.backend.core.constant.BackendConstant;
import info.hanniu.hanniupk.backend.core.enums.AnswerEnum;
import info.hanniu.hanniupk.backend.core.enums.BackendResponseStatusEnum;
import info.hanniu.hanniupk.backend.modular.dto.PassCustomsDTO;
import info.hanniu.hanniupk.backend.modular.service.IPassCustomsService;
import info.hanniu.hanniupk.backend.modular.vo.PassCustomsVO;
import info.hanniu.hanniupk.core.reqres.response.ResponseData;
import info.hanniu.hanniupk.kernel.logger.util.LogUtil;
import info.hanniu.hanniupk.kernel.model.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.service.impl
 * @ClassName: PassCustomsServiceImpl
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/19 16:13
 * @Version: 1.0
 */
@Service
public class PassCustomsServiceImpl implements IPassCustomsService {

    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    private PkSocketServiceImpl pkSocketService;

    @Override
    public ResponseData submit(PassCustomsDTO passCustomsDTO) {
        PassCustomsVO passCustomsVO = new PassCustomsVO();
        QuestionDTO questionDTO;
        LinkedHashMap<Integer, QuestionDTO> questionsMap;
        Integer result;
        if (null == passCustomsDTO.getQuestionId()) {
            // 第一次请求，随机选出50道问题，缓存到redis中，并返回第一题
            questionsMap = (LinkedHashMap<Integer, QuestionDTO>)this.redisService.getQuestionByRandom(BackendConstant.PASS_CUSTOMS_QUESTION_NUMBER);
            if (isNoQuestion(questionsMap)) {
                return resultNoQuestion(BackendResponseStatusEnum.PASS_CUSTOMS_NO_QUESTION_REPOSITORY.getCode(),
                        BackendResponseStatusEnum.PASS_CUSTOMS_NO_QUESTION_REPOSITORY.getMessage());
            }
            this.redisService.putPassCustomsQuestionRedis(passCustomsDTO.getUserId(),questionsMap);
            questionDTO = this.redisService.getNextQuestion(questionsMap,null);
            result = null;
        } else {
            // 验证答案，并返回下一题
            questionsMap = this.redisService.getPassCustomsQuestionByUserId(passCustomsDTO.getUserId());
            if (isNoQuestion(questionsMap)) {
                return resultNoQuestion(BackendResponseStatusEnum.PASS_CUSTOMS_NO_QUESTION_CACHE.getCode(),
                        BackendResponseStatusEnum.PASS_CUSTOMS_NO_QUESTION_CACHE.getMessage());
            }
            questionDTO = this.redisService.getNextQuestion(questionsMap,passCustomsDTO.getQuestionId());
            result = checkPassCustomsAnswer(questionsMap,passCustomsDTO.getQuestionId(),passCustomsDTO.getQuestionAnswerId());
        }
        Integer isHasNextQuestion = pkSocketService.isHasNextQuestion(questionDTO);
        passCustomsVO.setHasNextQuestion(isHasNextQuestion);
        passCustomsVO.setQuestion(questionDTO);
        passCustomsVO.setResult(result);
        if (noNextQuestion(isHasNextQuestion)) {
            this.redisService.deletePassCustomsQuestion(passCustomsDTO.getUserId());
        }
        return ResponseData.success(passCustomsVO);
    }

    private boolean noNextQuestion(Integer isHasNextQuestion) {
        return isHasNextQuestion.equals(AnswerEnum.ANSWER_FALSE.getCode());
    }

    private boolean isNoQuestion(LinkedHashMap<Integer, QuestionDTO> questionMap){
        return null == questionMap || questionMap.size() <= 0;
    }
    private ResponseData resultNoQuestion(Integer code,String message){
        return ResponseData.success(code,message,null);
    }

    private Integer checkPassCustomsAnswer(LinkedHashMap<Integer, QuestionDTO> questionsMap, Integer questionId,String questionAnswerId) {
        if (null == questionsMap || null == questionAnswerId) {
            return AnswerEnum.ANSWER_FALSE.getCode();
        }
        QuestionDTO questionDTO = questionsMap.get(questionId);
        if (null == questionDTO) {
            return AnswerEnum.ANSWER_FALSE.getCode();
        }
        if (questionAnswerId.equals(questionDTO.getRightAnswerNumber())) {
            return AnswerEnum.ANSWER_RIGHT.getCode();
        }
        LogUtil.info("checkPassCustomsAnswer--->questionDto:"+questionDTO.toString()+";questionId="+questionId+";answerId="+questionAnswerId);
        return AnswerEnum.ANSWER_FALSE.getCode();
    }
}
