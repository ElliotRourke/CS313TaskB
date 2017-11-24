import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class GuiDriver{

    private ThreadDriver td;
    private JTextField textFilter = new JTextField();
    private boolean isSearching = false;

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

    private void GUI() throws InterruptedException {
        JFrame mainFrame = new JFrame("JVM Thread Viewer");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, FlowLayout.CENTER));


        textFilter.setPreferredSize(new Dimension(120,20));

        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(120,20));
        buttonPanel.add(textField);
        JLabel filterLabel = new JLabel("Filter Thread Names");

        JButton addThreadButton = new JButton("Add New Thread");
        addThreadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                td.createNewThread(textField.getText());
                textField.setText("");
            }
        });

        buttonPanel.add(addThreadButton);
        buttonPanel.add(textFilter);
        buttonPanel.add(filterLabel);
        mainPanel.add(buttonPanel);
        mainFrame.add(mainPanel);

        JTable table;

        while(true) {
            table = refreshTable();
            JPanel tablePanel = new JPanel();
            JScrollPane scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            tablePanel.add(scrollPane);
            mainPanel.add(tablePanel);

            mainFrame.pack();
            mainFrame.setVisible(true);
            Thread.sleep(500);
            td.getThreads();
            mainPanel.remove(tablePanel);
            scrollPane.remove(table);
        }
    }

    public JTable refreshTable(){
        String[] columnNames = {"Thread Name", "ID", "State", "Priority", "Daemon","ThreadGroup"};
        JTable temp = new JTable(populateTable(),columnNames);
        temp.setSize(600,800);
        temp.getColumnModel().getColumn(0).setPreferredWidth(180);
        System.out.println(td.getThreadArray().length);

        if(!textFilter.getText().equals("")) {
            isSearching = true;
            TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(temp.getModel());
            temp.setRowSorter(rowSorter);
            textFilter.getDocument().addDocumentListener(new SearchFilter(textFilter, rowSorter));
            temp = new JTable(populateTable(),columnNames);
        }
        return temp;
    }

    public Object[][] populateTable(){

        Object data[][] = new Object[td.getThreadArray().length][6];
        if(textFilter.getText().equals("")) {
            for (int i = 0; i < td.getThreadArray().length; i++) {
                data[i][0] = td.getThreadArray()[i].getName();
                data[i][1] = td.getThreadArray()[i].getId();
                data[i][2] = td.getThreadArray()[i].getState();
                data[i][3] = td.getThreadArray()[i].getPriority();
                data[i][4] = td.getThreadArray()[i].isDaemon();
                data[i][5] = td.getThreadArray()[i].getThreadGroup();
            }
        }else if(isSearching){
            for (int i = 0; i < td.getThreadArray().length; i++) {
                if(td.getThreadArray()[i].getName().toLowerCase().equals(textFilter.getText().toLowerCase())) {
                    data[i][0] = td.getThreadArray()[i].getName();
                    data[i][1] = td.getThreadArray()[i].getId();
                    data[i][2] = td.getThreadArray()[i].getState();
                    data[i][3] = td.getThreadArray()[i].getPriority();
                    data[i][4] = td.getThreadArray()[i].isDaemon();
                    data[i][5] = td.getThreadArray()[i].getThreadGroup();
                }
            }
        }
        return data;
    }
}
