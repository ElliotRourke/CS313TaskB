package main;

//TODO
// STOP THREADS
// SEARCH THREADS (DYNAMICALLY)
// LOOK AT THREAD GROUPS

import drivers.TestDriveSeven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private ThreadDriver td;
    private GuiTableController tableBuilder;
    private JTextField textFilter;
    private boolean isSearch = false;
    private boolean isFilter = false;

    public GUI(ThreadDriver td){
        this.td = td;
        tableBuilder = new GuiTableController(td);
        buildGUI();
    }

    private void buildGUI(){
        JFrame mainFrame = new JFrame("JVM Thread Viewer");
        mainFrame.setPreferredSize(new Dimension(800,800));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel searchPanel = new JPanel();

        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(50,20));

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(textFilter.getText().equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Sorry you cannot search for a thread with no name!");
                }else{
                    isSearch = true;
                }
            }
        });


        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(textFilter.getText().equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Sorry you cannot filter a thread group with no name!");
                }else{
                    isFilter = true;
                }
            }
        });

        textFilter = new JTextField(20);
        textFilter.setPreferredSize(new Dimension(50,20));
        searchPanel.add(textFilter);
        searchPanel.add(searchButton);
        searchPanel.add(filterButton);

        JButton addThreadButton = new JButton("Start");
        addThreadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!textField.getText().equals("")){
                    td.createNewThread(textField.getText());
                    textField.setText("");
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Sorry you cannot add a thread with no name!");
                }
            }
        });

        JButton stopThreadButton = new JButton("Stop");
        stopThreadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!textField.getText().equals("")){
                    td.killThread(textField.getText());
                    textField.setText("");
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Sorry you cannot stop a thread with no name!");
                }
            }
        });

        buttonPanel.add(textField);
        buttonPanel.add(stopThreadButton);
        buttonPanel.add(addThreadButton);

        mainFrame.add(mainPanel,BorderLayout.CENTER);
        mainFrame.add(buttonPanel,BorderLayout.NORTH);
        mainFrame.add(searchPanel,BorderLayout.SOUTH);

        while(true){

            if(textFilter.getText().equals("")){
                isFilter = false;
                isSearch = false;
            }

            JPanel tempPanel = refresh();
            mainPanel.add(tempPanel, BorderLayout.SOUTH);
            mainFrame.setVisible(true);
            mainFrame.pack();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null,
                        "Sorry you cannot interrupt this thread!");
            }
            td.getAllThreads();
            mainPanel.remove(tempPanel);
        }
    }

    private JPanel refresh(){
        JPanel tablePanel = new JPanel();
        JScrollPane scrollPane = null;
        if(!isSearch || ! isFilter || textFilter.getText().equals("")){
            new TestDriveSeven();
            scrollPane = new JScrollPane(new GuiTableController(td).buildTable());
        }

        if(isSearch){
            new TestDriveSeven();
            scrollPane = new JScrollPane(new GuiTableController(td).buildTable(textFilter.getText()));
        }

        if(isFilter){
            new TestDriveSeven();
            scrollPane = new JScrollPane(new GuiTableController(td).buildFilterTable(textFilter.getText()));
        }

        tablePanel.add(scrollPane);
        return tablePanel;
    }

}
