package main;

public class ThreadDriver {

    private Thread[] threads;
    private ThreadGroup rootGroup;
    private ThreadGroup newThreads;

    public ThreadDriver(){
        getRootThreadGroup();
        getAllThreads();
        createThreadGroups();
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

    public void createThreadGroups(){
        ThreadGroup a = new ThreadGroup("A");
        ThreadGroup b = new ThreadGroup("B");
        ThreadGroup c = new ThreadGroup(a,"C");

        Thread threada = new Thread(a,() ->
        {
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException e) {
                //Do nothing
            }
        }, "Thread A");
        threada.start();

        Thread threadb = new Thread(b,() ->
        {
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException e) {
                //Do nothing
            }
        }, "Thread B");
        threadb.start();

        Thread threadc = new Thread(c,() ->
        {
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException e) {
                //Do nothing
            }
        }, "Thread C");
        threadc.start();


        getAllThreads();
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
