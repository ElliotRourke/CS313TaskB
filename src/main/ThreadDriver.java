package main;

public class ThreadDriver {

    private Thread[] threads;
    private ThreadGroup rootGroup;
    private ThreadGroup newThreads;

    public ThreadDriver(){
        getRootThreadGroup();
        getAllThreads();
        newThreads = new ThreadGroup("Your Threads");
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

        if(name.equals("")){
            name = "Blank Thread";
        }

        Thread thread = new Thread(newThreads,() ->
        {
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException e) {
                //Do nothing
            }
        }, name);
        thread.start();
        getAllThreads();
    }

    public void killThread(String name){
        for (Thread t : getAllThreads()){
            if(t.getName().toLowerCase().equals(name.toLowerCase())){
                t.interrupt();
            }
        }
    }

}
