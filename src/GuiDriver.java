import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import javax.swing.*;


public class GuiDriver{

    private ThreadDriver td;
    private JFrame frame;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JTable table;
    private JButton addThreadButton;

    public GuiDriver(){
        Thread root = new Thread("dog");
        td = new ThreadDriver(root);
        try {
            //displayThreadInfo();
            GUI();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void displayThreadInfo() throws InterruptedException {
        for (int i = 0; i < td.getThreadArray().length; i++) {
            System.out.println("\n********** START ************");
            System.out.println("Name : " + td.getThreadArray()[i].getName());
            System.out.println("ID : " + td.getThreadArray()[i].getId());
            System.out.println("Thread Group : " + td.getThreadArray()[i].getThreadGroup().getName());
            System.out.println("State : " + td.getThreadArray()[i].getState());
            System.out.println("Priority : " + td.getThreadArray()[i].getPriority());
            System.out.println("Is Daemon : " + td.getThreadArray()[i].isDaemon());
            System.out.println("********** END ************\n");
        }
    }

    public void GUI() throws InterruptedException {
        frame = new JFrame("JVM Thread Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while(true) {
            table = refreshTable();

            scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);

            addThreadButton = new JButton("Add New Thread");
            addThreadButton.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                Thread temp = new Thread("Dummy");
                                                td.setThreadCount(td.getThreadCount()+1);
                                                td.setThreadArray();
                                                //td.getThreadArray()[td.getThreadCount()] = temp;
                                                td.threadGroupEnumerate(td.getThreadArray());
                                                refreshTable();
                                            }
                                        });
            panel = new JPanel();
            panel.add(addThreadButton);
            panel.add(scrollPane);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            Thread.sleep(500);
            td.getAllThreadGroups();
            td.threadGroupEnumerate(td.getThreadArray());
            frame.remove(panel);
            scrollPane.remove(table);
        }
    }

    public JTable refreshTable(){
        String[] columnNames = {"Thread Name", "ID", "State", "Priority", "Daemon","ThreadGroup"};
        JTable table = null;
        table = new JTable(populateTable(),columnNames);
        table.setSize(600,800);
        return table;
    }

    public Object[][] populateTable(){
        Object data[][] = new Object[td.getThreadCount()][6];
        for(int i=0; i < td.getThreadCount();i++){
            data[i][0]=td.getThreadArray()[i].getName();
            data[i][1]=td.getThreadArray()[i].getId();
            data[i][2]=td.getThreadArray()[i].getState();
            data[i][3]=td.getThreadArray()[i].getPriority();
            data[i][4]=td.getThreadArray()[i].isDaemon();
            data[i][5]=td.getThreadArray()[i].getThreadGroup();
        }
        return data;
    }

}
