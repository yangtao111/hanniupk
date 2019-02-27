package info.hanniu.hanniupk.backend;

import java.util.concurrent.Exchanger;

/**
 * @ProjectName: hanniupk-backend
 * @Package: info.hanniu.hanniupk.backend
 * @Author: zhanglj
 * @Description: TODO
 * @Date: 2018/10/31 7:47 PM
 * @Version: 1.0.0
 */
public class ExchangerTest {
    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();
        for (int i = 0; i < 3; i++) {
            new A(ex, i).start();
            new B(ex, i).start();
        }
    }
}

class A extends Thread {

    private Exchanger<String> ex;
    private String id = "A";

    public A(Exchanger<String> ex,int i) {
        this.ex = ex;
        this.id+="_"+i;
    }

    public void run() {
        String str = null;
        try {
            str = ex.exchange(this.id);
            System.out.println(this.id+"-------"+str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class B extends Thread {

    private Exchanger<String> ex;
    private String id = "B";

    public B(Exchanger<String> ex, int i) {
        this.ex = ex;
        this.id+="_"+i;
    }

    public void run() {
        String str = null;
        try {
            str = ex.exchange(this.id);
            System.out.println(this.id+"-------"+str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
