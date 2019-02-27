package info.hanniu.hanniupk.backend;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend
 * @Author: zhanglj
 * @Description: TODO
 * @Date: 2018/10/31 7:47 PM
 * @Version: 1.0.0
 */
public class StudyTest {

        public static int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();

            for (int i = 0; i < nums.length; i++) {
                int result = target-nums[i];
                if(map.containsKey(result)) {
                    return new int[]{map.get(result), i};
                }
                map.put(nums[i], i);
            }
            throw new RuntimeException("no have");
        }


    public static int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int result = target - nums[i];
            if(map.containsKey(result) && map.get(result) != i) {
                return new int[]{i, map.get(result)};
            }
        }
        throw new RuntimeException("no have");
    }

//    public static int[] twoSum(int[] nums, int target) {
//        for(int i=0;i<nums.length;i++) {
//            for(int j=i+1;j<nums.length;j++) {
//                if(nums[i]+nums[j]==target) {
//                    int[] result = {i,j};
//                    return result;
//                }
//            }
//        }
//        throw new RuntimeException("没有符合条件的两个数");
//    }

    public static void main(String[] args) {

        int[] test = {2, 5, 5, 1};
        int[] result = twoSum1(test, 10);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+" ");
        }

    }

}

