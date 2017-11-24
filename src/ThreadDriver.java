
public class ThreadDriver {

    private ThreadGroup root;
    private Thread[] threads;



    public ThreadDriver(Thread rootThread) {
        getRoot();
        getThreads();
    }

    public void getRoot(){
        Thread rootThread = new Thread("rootThread");
        root = rootThread.getThreadGroup();
        while (root.getParent()!=null){
            root = root.getParent();
        }
    }

    public void getThreads(){
        threads = new Thread[root.activeCount()];
        root.enumerate(threads);
    }

    public void createNewThread(String name){

        Thread thread = new Thread(() ->
        {
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }, name);
        thread.start();
        getThreads();
    }

    public void enumerateThreads(){
        root.enumerate(threads);
    }

    public void setThreadArray(Thread[] temp){
        this.threads = temp;
    }

    public Thread[] getThreadArray(){
        return threads;
    }

}
