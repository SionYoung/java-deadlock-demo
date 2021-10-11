
public class  DeadLockDemo{
    public static void main(String[] args){
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(new DeadlockTask(lock1, lock2, true), "thread 1").start();
        new Thread(new DeadlockTask(lock1, lock2, false), "thread 2").start();
    }

    static class DeadlockTask implements Runnable{

        private Object lock1;
        private Object lock2;
        private boolean flag;

        public DeadlockTask(Object lock1, Object lock2, boolean flag){
            this.lock1 = lock1;
            this.lock2 = lock2;
            this.flag = flag;
        }

        @Override
        public void run(){
            if(flag){
                synchronized(lock1){
                    try {
                        Thread.sleep(5000);
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "-> waiting for lock1 to be released");
                    synchronized(lock2){
                        System.out.println(Thread.currentThread().getName() + "->  get lock 2");
                    }
                }
            } else{
                synchronized(lock2){
                    try {
                        Thread.sleep(5000);
                    }catch(InterruptedException ex){
                        ex.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "-> waiting for lock2 to be released");
                    synchronized(lock1){
                        System.out.println(Thread.currentThread().getName() + "->  get lock 1");
                    }
                }
            }
        }
    }
}