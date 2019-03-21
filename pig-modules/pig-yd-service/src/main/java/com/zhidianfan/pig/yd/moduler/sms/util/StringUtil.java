package com.zhidianfan.pig.yd.moduler.sms.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by Administrator on 2017/12/18.
 */
public class StringUtil {
    /**
     * 验证手机号码
     *
     * 移动号码段:134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 联通号码段:130、131、132、145、155、156、166、175、176、185、186
     * 电信号码段:133、149、153、173、177、180、181、189、199
     * 其他号段:
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cellphone);
        return m.matches();
    }

    /**
     * 验证固话号码
     *
     * @param telephone
     * @return
     */
    public static boolean checkTelephone(String telephone) {
        String regex = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(telephone);
        return m.matches();
    }

    /**
     * string转 list
     * @param str 由 , 分隔的字符串
     * @return
     */
    public static List<String> string2List(String str) {
        String[] array = str.split(",");
        List<String> result = new ArrayList<String>();
        for (String obj : array) {
            if (!"".equals(obj)){
                result.add(obj);
            }
        }
        return result;
    }

}
