package info.hanniu.hanniupk.kernel.model.util;

import javafx.beans.binding.When;

/**
 * @ProjectName: hanniupk-kernel
 * @Package: info.hanniu.hanniupk.kernel.model.util
 * @ClassName: WxGenderTransUtil
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/10/13 17:28
 * @Version: 1.0
 */
public class WxGenderTransUtil {

    private final static String HAN_NIU_SEX_MALE = "M";
    private final static String HAN_NIU_SEX_FEMALE = "F";
    private final static String HAN_NIU_SEX_UN_KNOWN="N";

    private final static int WX_SEX_MALE = 1;
    private final static int WX_SEX_FEMALE = 2;
    private final static int WX_SEX_UN_KNOWN = 0;

    public static String wX2hanNiu(Integer gender){
        String resultSex ;
        switch (gender) {
            case WX_SEX_MALE:
                resultSex = HAN_NIU_SEX_MALE;
                break;
            case WX_SEX_FEMALE:
                resultSex = HAN_NIU_SEX_FEMALE;
                break;
            case WX_SEX_UN_KNOWN:
                resultSex = HAN_NIU_SEX_UN_KNOWN;
                break;
                default:
                    resultSex = HAN_NIU_SEX_UN_KNOWN;
        }
        return resultSex;
    }
    public static Integer hanNiu2Wx(String gender){
        Integer resultSex ;
        switch (gender) {
            case HAN_NIU_SEX_MALE:
                resultSex = WX_SEX_MALE;
                break;
            case HAN_NIU_SEX_FEMALE:
                resultSex = WX_SEX_FEMALE;
                break;
            case HAN_NIU_SEX_UN_KNOWN:
                resultSex = WX_SEX_UN_KNOWN;
                break;
                default:
                    resultSex = WX_SEX_UN_KNOWN;
        }
        return resultSex;
    }

}
