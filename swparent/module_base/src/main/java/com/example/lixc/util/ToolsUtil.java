package com.example.lixc.util;

import com.example.lixc.enums.ResultJsonEnum;
import com.example.lixc.exception.URLException;
import com.example.lixc.template.SimpleMessageTemplate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Configuration
public class ToolsUtil {

    //昵称校验 由最多8位中文、英文、下划线、数字组成
    public static boolean regexNickName(String name) {
        //子账户名称验证
        String regex = "^[\u4e00-\u9fa5a-zA-Z0-9_]{1,8}$";
        return regex(name, regex);
    }

    /**
     * 校验密码
     * 能匹配的组合为：数字+字母，数字+特殊字符，字母+特殊字符，数字+字母+特殊字符组合，而且不能是纯数字，纯字母，纯特殊字符
     *
     * @param pass
     * @return
     */
    public static boolean regexPass(String pass) {
        String regex = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{8,20}$";
        return regex(pass, regex);
    }

    public static boolean regexEmail(String mail) {
        //邮箱验证
        String regex = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return regex(mail, regex);
    }

    public static boolean regexPhone(String phone) {
        //手机验证
        //		String regex ="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        String regex = "^1\\d{10}$";
        return regex(phone, regex);
    }

    public static boolean regexName(String name) {
        //子账户名称验证
        String regex = "^[\u4e00-\u9fa5a-zA-Z0-9]{1,10}$";
        return regex(name, regex);
    }

    public static boolean regexDoctorName(String name) {
        String regex = "^[\u4e00-\u9fa5.·]{0,20}$";
        return regex(name, regex);
    }


    public static boolean regexNum(String regexNum) {
        //员工号验证
        String regex = "^[a-zA-Z0-9]{1,30}$";
        return regex(regexNum, regex);
    }

    public static boolean regexPicture(String format) {
        //图片格式验证
        String regex = "^jpeg|jpg|png$";
        return regex(format.toLowerCase(), regex);
    }

    private static boolean regex(String flag, String regex) {
        Pattern compile = Pattern.compile(regex);
        if (compile.matcher(flag).matches()) {
            return true;
        }
        return false;
    }

    public static boolean regexIdCode(String idCode) {
        //身份证验证
        String regex = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        return regex(idCode, regex);
    }

    /**
     * <p>Title: getObjectListIsNotNull</p>
     * <p>Description: 如果list不为空返回list第一个对象</p>
     *
     * @param <T>
     * @param list
     * @return
     * @author wen_guoxing
     * @date 2018年10月23日
     */
    public static <T> T getObjectListIsNotNull(List<T> list) {
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    /***
     * 生成数字的随机数
     * @param digit 位数
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String randomByDigit(int digit) throws NoSuchAlgorithmException {
        Random random = null;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("windows") != -1) {
            random = SecureRandom.getInstanceStrong();
        } else {
            random = SecureRandom.getInstance("NativePRNGNonBlocking");
        }
        String cardNnumer = "";

        for (int i = 0; i < digit; i++) {
            cardNnumer += random.nextInt(10);//生成12位数字
        }
        return cardNnumer;
    }


    /**
     * 校验参数是否验证通过
     *
     * @param verifyParams
     * @return true通过 false失败
     */
    public static boolean verifyParams(ResultJson verifyParams) {
        if (verifyParams.getStatus() == ResultJsonEnum.ERROR_CODE.getCode()) {
            return false;
        }
        return true;
    }

    /**
     * UUID
     * 去掉 “-”
     * 随机数
     *
     * @return
     */
    public static String getFileNameByUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取图片格式的（jpg）文件名
     *
     * @return
     */
    public static String getImageNameByUUID() {
        return getFileNameByUUID() + ".jpg";
    }


    private static final String BASE_CODE_STRING = "0123456789ABCDEFGHJKLMNPQRTUWXY";
    private static final char[] BASE_CODE_ARRAY = BASE_CODE_STRING.toCharArray();
    private static final List<Character> BASE_CODES = new ArrayList<>();
    private static final String BASE_CODE_REGEX = "[" + BASE_CODE_STRING + "]{18}";
    private static final int[] WEIGHT = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
    private static final String REGEX = "^[A-Z0-9]{18}$";
    private static final String IDCARD_REGEX = "^[A-Z0-9]{30}$";
    private static final String CHINESE_REGEX = "[\u4e00-\u9fa5\uf900-\ufa2d·] {0,30}$";
    private static final String TITLE_REGEX = "^[A-Z0-9\\u4e00-\\u9fa5\\uf900-\\ufa2d]{20}$";

    static {
        for (char c : BASE_CODE_ARRAY) {
            BASE_CODES.add(c);
        }
    }

    /**
     * 简单校验统一社会信用代码
     * 18位（大写字母+数字）
     *
     * @param creditCode 统一社会信用代码
     * @return 校验结果
     */
    public static boolean isCreditCodeBySimple(String creditCode) {
        if (StringUtils.isBlank(creditCode)) {
            return false;
        }
        return Pattern.matches(REGEX, creditCode);
    }

    /**
     * 是否是有效的统一社会信用代码
     *
     * @param creditCode 统一社会信用代码
     * @return 校验结果
     */
    public static boolean isCreditCode(String creditCode) {
        if (StringUtils.isBlank(creditCode) || !Pattern.matches(BASE_CODE_REGEX, creditCode)) {
            return false;
        }
        char[] businessCodeArray = creditCode.toCharArray();
        char check = businessCodeArray[17];
        int sum = 0, length = 17;
        for (int i = 0; i < length; i++) {
            char key = businessCodeArray[i];
            sum += (BASE_CODES.indexOf(key) * WEIGHT[i]);
        }
        int value = 31 - sum % 31;
        return check == BASE_CODE_ARRAY[value % 31];
    }

    /**
     * @method generateWord
     * 生成4位随机数  数字字母混合
     */
    public static String generateWord(int start, int end) {
        String[] beforeShuffle = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(start, end);
        return result;
    }


    /**
     * 增强判断空  包含 null
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {

        if (StringUtils.isBlank(value) || "null".equals(value)) {
            return true;
        }
        return false;

    }

    /**
     * 增强判断空  不是空
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {

        return !isEmpty(value);

    }

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将当前数字转换为几位数字，不足前面补0
     *
     * @param number 要转换的数字
     * @param count  转换为几位数，不足前面补0
     * @return
     */
    public static String convertNumToDigitString(int number, int count) {
        return String.format("%" + count + "d", number).replace(" ", "0");//5代表总共是几位数
    }

    /**
     * 保存4位数，不足前面补0，最前面补充标识，转换出的数字，默认自增1
     *
     * @param sourceNumber 转换数字
     * @param sign         标识
     * @return
     */
    public static String numberJointTargerSign(int sourceNumber, String sign) {
        if (sourceNumber >= 999) {
            throw new IndexOutOfBoundsException();
        }
        StringBuffer stringBuffer = new StringBuffer(sign);
        return stringBuffer.append(convertNumToDigitString(++sourceNumber, 4)).toString();
    }

    /* 中文正则
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        return Pattern.matches(CHINESE_REGEX, str);

    }

    /* 英文数字，最大30位
     * @param str
     * @return
     */
    public static boolean regexIdcard(String str) {
        return Pattern.matches(IDCARD_REGEX, str);

    }

    /* 中英文数字，最大20位
     * @param str
     * @return
     */
    public static boolean regexTitle(String str) {
        return Pattern.matches(TITLE_REGEX, str);

    }

    public static String getCode() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        return idWorker.nextId().toString();
    }


    /**
     * URL编码之后的参数，转换为正常的参数
     *
     * @param param
     * @throws URLException
     */

    public static String convertURL(String param) throws URLException {

        if (param != null && param.contains("%")) {
            try {
                param = URLDecoder.decode(param, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new URLException("参数格式错误，参数为：" + param + "", e);
            }
        }

        return param;
    }

    /**
     * 是否包含数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 替换模板中的占位符
     *
     * @param template
     * @param map
     * @return
     */
    public static String replaceTemplate(String template, Map<String, String> map) {
        if (StringUtils.isNotBlank(template) && map != null && map.keySet().size() > 0) {
            String regex = "\\{(.*?)\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(template);
            StringBuilder builder = new StringBuilder(template);
            while (matcher.find()) {
                String key = matcher.group(1);
                if (!key.contains("\\{") && !key.contains("\\}")) {
                    String value = map.get(key);
                    int start = builder.lastIndexOf("{" + key + "}");
                    int end = start + key.length() + 2;
                    builder.replace(start, end, value);
                }
            }
            return builder.toString();
        }
        return null;
    }

    public static TreeSet<String> getDaysList(int days) {
        TreeSet<String> daysList = new TreeSet<>();
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date nowDate = new Date();
        String format = "";
        calendar.setTime(nowDate);
        for (int i = 0; i < days; i++) {
            calendar.add(Calendar.DATE, -1);
            format = sdf.format(calendar.getTime());
            daysList.add(format);
        }
        return daysList;
    }

}
