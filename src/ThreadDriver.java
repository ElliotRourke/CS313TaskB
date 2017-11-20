
public class ThreadDriver {

    private Thread rootThread;
    private Thread[] threads;
    private ThreadGroup[] allThreadGroups;
    private int threadCount;


    public ThreadDriver(Thread rootThread) {
        setRootThread(rootThread);
        setAllThreadGroups(findThreadGroups());
        threadCount= allThreadGroups[0].activeCount();
        setThreadCount(threadCount);
        setThreadArray();
        threadGroupEnumerate(getThreadArray());
    }

    public ThreadGroup getRootGroup(){

        ThreadGroup parentGroup ;

        ThreadGroup root = getRootThread().getThreadGroup();

        while ((parentGroup = root.getParent()) != null){
            root = parentGroup;
        }
        return root;
    }

    public ThreadGroup [] findThreadGroups(){
        ThreadGroup root = getRootGroup();

        int count = root.activeGroupCount();

        ThreadGroup[] threadGroups = new ThreadGroup[count];

        root.enumerate(threadGroups);

        ThreadGroup [] finalThreadGroup = new ThreadGroup[count+1];

        finalThreadGroup[0] = root;

        for(int i = 1; i < count+1; i++){
            finalThreadGroup[i] = threadGroups[i-1];
        }

        return finalThreadGroup;
    }

    public void setRootThread(Thread rootThread){
        this.rootThread = rootThread;
    }

    public Thread getRootThread(){
        return rootThread;
    }

    public void threadGroupEnumerate(Thread[] threads){
        allThreadGroups[0].enumerate(threads);
    }

    public void setThreadCount( int threadCount){
        this.threadCount = threadCount;
    }

    public int getThreadCount(){
        return threadCount;
    }

    public void setAllThreadGroups(ThreadGroup[] allThreadGroups){
        this.allThreadGroups = allThreadGroups;
    }

    public ThreadGroup[] getAllThreadGroups(){
        return allThreadGroups;
    }

    public void setThreadArray(){
        this.threads = new Thread[getThreadCount()];
    }

    public Thread[] getThreadArray(){
        return threads;
    }

}
