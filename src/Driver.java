import java.lang.*;
import java.util.Arrays;

public class Driver{

    private ThreadGroup currentThreadGroup;
    private ThreadGroup currentThreadGroup1;
    private Thread thread;
    private Thread ciaransThread;

    public static void main(String[] args) {
       Driver d1 = new Driver();

        ThreadGroup[] allThreadGroups = d1.getAllThreadGroup();

        int threadCount = allThreadGroups[0].activeCount();

        Thread[] threads = new Thread[threadCount];

        allThreadGroups[0].enumerate(threads);

        System.out.println(Arrays.toString(threads));
    }


    private Driver(){
        currentThreadGroup = new ThreadGroup("Elliot");
        currentThreadGroup1 = new ThreadGroup("Ciaran");

        thread = new Thread(currentThreadGroup, "thread1");
        ciaransThread = new Thread(currentThreadGroup1,"thread2");

        thread.start();
        ciaransThread.start();
    }

    private ThreadGroup getRoot(){
        //System.out.println("Hello, " + thread.getThreadGroup());

        ThreadGroup parentGroup ;//= new ThreadGroup();

        ThreadGroup root = currentThreadGroup;

        while ((parentGroup = root.getParent()) != null){
            root = parentGroup;
        }
        return root;
    }

    private ThreadGroup [] getAllThreadGroup(){

        ThreadGroup root = getRoot();

        int count = root.activeGroupCount();

        System.out.println("Number of groups in this thread group = " + (count+1));

        ThreadGroup[] threadGroups = new ThreadGroup[count];

        root.enumerate(threadGroups);

        ThreadGroup [] finalThreadGroup = new ThreadGroup[count+1];

        finalThreadGroup[0] = root;

        for(int i = 1; i < count+1; i++){
            finalThreadGroup[i] = threadGroups[i-1];
        }


        return finalThreadGroup;
    }

}
