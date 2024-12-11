
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is working..");
    }

    public static void main(String[] args) {

        MyThread thread = new MyThread();
        thread.start();

    }

}
