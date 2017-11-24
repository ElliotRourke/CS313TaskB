package main;

import javax.swing.*;

public class GuiTableController extends JTable {


    private ThreadDriver td;

    public GuiTableController(ThreadDriver td){
        this.td = td;
    }


    public JTable buildTable(){
        String[] columnNames = {"Thread Name", "ID", "State", "Priority", "Daemon","ThreadGroup"};

        Object data[][] = new Object[td.getAllThreads().length][6];
        for (int i = 0; i < td.getAllThreads().length; i++) {
            data[i][0] = td.getAllThreads()[i].getName();
            data[i][1] = td.getAllThreads()[i].getId();
            data[i][2] = td.getAllThreads()[i].getState();
            data[i][3] = td.getAllThreads()[i].getPriority();
            data[i][4] = td.getAllThreads()[i].isDaemon();
            data[i][5] = td.getAllThreads()[i].getThreadGroup();
        }

        JTable table = new JTable(data,columnNames);
        table.setSize(600,800);
        table.getColumnModel().getColumn(0).setPreferredWidth(180);
        return table;
    }


    public JTable buildTable(String name){
        String[] columnNames = {"Thread Name", "ID", "State", "Priority", "Daemon","ThreadGroup"};

        Thread[] allThreads = td.getAllThreads();
        Thread[] onesWeWant = new Thread[allThreads.length];

        int index = 0;
        for(Thread t : allThreads){
            if(t.getName().equals(name)){
                onesWeWant[index] = t;
                index++;
            }
        }

        System.out.println("NAME : " + onesWeWant.length);

        Object data[][] = new Object[index][6];
        for (int i = 0; i < index; i++) {
            data[i][0] = onesWeWant[i].getName();
            data[i][1] = onesWeWant[i].getId();
            data[i][2] = onesWeWant[i].getState();
            data[i][3] = onesWeWant[i].getPriority();
            data[i][4] = onesWeWant[i].isDaemon();
            data[i][5] = onesWeWant[i].getThreadGroup();
        }

        JTable table = new JTable(data,columnNames);
        table.setSize(600,800);
        table.getColumnModel().getColumn(0).setPreferredWidth(180);
        return table;
    }
}
