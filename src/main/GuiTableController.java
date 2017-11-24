package main;

import javax.swing.*;
import java.awt.*;

public class GuiTableController extends JTable {


    private ThreadDriver td;
    private String[] columnNames = {"Thread Name", "ID", "State", "Priority", "Daemon","ThreadGroup"};

    public GuiTableController(ThreadDriver td){
        this.td = td;
    }


    public JTable buildTable(){
        Object data[][] = new Object[td.getAllThreads().length][6];
        for (int i = 0; i < td.getAllThreads().length; i++) {
            data[i][0] = td.getAllThreads()[i].getName();
            data[i][1] = td.getAllThreads()[i].getId();
            data[i][2] = td.getAllThreads()[i].getState();
            data[i][3] = td.getAllThreads()[i].getPriority();
            data[i][4] = td.getAllThreads()[i].isDaemon();
            data[i][5] = td.getAllThreads()[i].getThreadGroup().getName();
        }

        JTable table = new JTable(data,columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(800,800));
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        return table;
    }


    public JTable buildTable(String name){
        Thread[] allThreads = td.getAllThreads();
        Thread[] onesWeWant = new Thread[allThreads.length];

        int index = 0;
        for(Thread t : allThreads){
            if(t.getName().equals(name)){
                onesWeWant[index] = t;
                index++;
            }
        }

        Object data[][] = new Object[index][6];
        for (int i = 0; i < index; i++) {
            data[i][0] = onesWeWant[i].getName();
            data[i][1] = onesWeWant[i].getId();
            data[i][2] = onesWeWant[i].getState();
            data[i][3] = onesWeWant[i].getPriority();
            data[i][4] = onesWeWant[i].isDaemon();
            data[i][5] = onesWeWant[i].getThreadGroup().getName();
        }

        JTable table = new JTable(data,columnNames);
        table.setSize(600,800);
        table.setPreferredScrollableViewportSize(new Dimension(800,800));
        table.getColumnModel().getColumn(0).setPreferredWidth(180);
        return table;
    }
}
