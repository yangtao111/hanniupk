package info.hanniu.hanniupk.backend.util;

import java.util.Random;

/**
 * @author alan
 */
public class RandomUtils {

	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMBER_CHAR = "0123456789";

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 *
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 *
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 *
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 *
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 *
	 * @param length 字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 *
	 * @param num       数字
	 * @param fixdlenth 字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 *
	 * @param num       数字
	 * @param fixdlenth 字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(int num, int fixdlenth) {
		return toFixdLengthString((long) num, fixdlenth);
	}

	/**
	 * 生成一个定长随机字符串（只包含纯数字）
	 *
	 * @param length
	 * @return
	 */
	public static String generateFixdNumString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = generateRandom(NUMBER_CHAR.length());
			sb.append(NUMBER_CHAR.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成0-num之间的随机数
	 *
	 * @param num
	 * @return
	 */
	public static int generateRandom(int num) {
		Random random = new Random();
		return random.nextInt(num);
	}

	/**
	 * 生成min到num+min之前的随机数
	 *
	 * @param num
	 * @param min
	 * @return
	 */
	public static int generateRandom(int num, int min) {
		Random random = new Random();
		return random.nextInt(num) + min;
	}

	/**
	 * 利用12位随机数种子生成0-max之间的随机数
	 *
	 * @return
	 */
	public static int getRandomBySeed(int max) {
		int seed = generateRandom(999999999);
		Random random = new Random(seed);
		return random.nextInt(max);
	}

}
