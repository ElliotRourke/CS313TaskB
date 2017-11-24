package main;


public class ThreadDriver {

    private Thread[] threads;
    private ThreadGroup rootGroup;

    public ThreadDriver(){
        getRootThreadGroup();
        getAllThreads();
    }


    public ThreadGroup getRootThreadGroup(){
        rootGroup = Thread.currentThread().getThreadGroup();
        while (rootGroup.getParent()!= null){
            rootGroup = rootGroup.getParent();
        }
        return rootGroup;
    }


    public Thread[] getAllThreads(){
        int estimate = getRootThreadGroup().activeCount();
        threads = new Thread[estimate];
        rootGroup.enumerate(threads);
        return threads;
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
        getAllThreads();
    }

}
