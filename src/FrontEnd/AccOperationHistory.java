package FrontEnd;

import BackEnd.Account;
import BackEnd.OperationDir.Operation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccOperationHistory extends JFrame {
    AccOperationHistory thisobj;
    JTable table;
    JScrollPane scrollPane;
    JButton BackButton;
    JButton CreateNew;
    JButton Delete;
    JButton Edit;
    JPanel BottomButtons;
    AccOperationHistory(Account account, AccountsWin previousWin){
        thisobj = this;
        setSize(1000,700);
        setMinimumSize(new Dimension(450,300));
        setLocationRelativeTo(previousWin);
        setTitle("Historia Operacji");
        setLayout(new BorderLayout(10,10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        BottomButtons = new JPanel();
        BottomButtons.setPreferredSize(new Dimension(980,40));

        String[] columnName = {"ID", "Dzień", "Tag", "Wartość", "Bilans", "Opis"};
        String[][] data = account.getData();
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(960,620));

        table = new JTable(new javax.swing.table.DefaultTableModel (data, columnName));

        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(1000);
        table.getColumnModel().getColumn(1).setMinWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMinWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(510);

        scrollPane.setViewportView(table);
        add(scrollPane,BorderLayout.CENTER);

        BackButton = new JButton("Cofnij");
        BackButton.setSize(new Dimension(200,20));
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWin.setVisible(true);
                dispose();
            }
        });
        BottomButtons.add(BackButton);

        CreateNew = new JButton("Nowa operacja");
        CreateNew.setSize(200,20);
        CreateNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO
            }
        });
        BottomButtons.add(CreateNew);

        Delete = new JButton("Usun");
        Delete.setSize(new Dimension(200,20));
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ////TODO
                System.out.println(table.getSelectedRow());
            }
        });
        BottomButtons.add(Delete);

        Edit = new JButton("Edytuj");
        Edit.setSize( 200,20);
        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///todo
            }
        });
        BottomButtons.add(Edit);
        add(BottomButtons, BorderLayout.PAGE_END);
    }
    
}
