package info.hanniu.hanniupk.backend.modular.dto;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend.modular.dto
 * @ClassName: Test
 * @Author: tao
 * @Description: ${description}
 * @Date: 2018/11/5 16:26
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N,new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable"+Thread.currentThread().getName());
            }
        });

        for(int i=0;i<4;i++)
            new Writer(barrier,"player_"+i).start();
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        private String playerName;
        public Writer(CyclicBarrier cyclicBarrier,String playerName) {
            this.cyclicBarrier = cyclicBarrier;
            this.playerName = playerName;
        }

        @Override
        public void run() {
            try {
                System.out.println("name:"+playerName+"insert!");
                Thread.sleep(1000);      //以睡眠来模拟写入数据操作
                try {
                    cyclicBarrier.await(5, TimeUnit.SECONDS);
                    System.out.println("name:"+playerName+"await over!");
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    System.out.println("name:"+playerName+"await() error!");
                }
                System.out.println("name:"+playerName+"await side over!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
        }
    }
}
