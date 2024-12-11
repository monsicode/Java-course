public class MyThreadWithRunnable implements Runnable {

    @Override
    public void run() {
        System.out.printf("My other thread is working");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyThreadWithRunnable());
        thread.start();
    }

}
