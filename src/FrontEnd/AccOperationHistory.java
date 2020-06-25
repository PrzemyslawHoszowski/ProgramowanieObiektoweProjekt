package FrontEnd;

import BackEnd.Account;
import BackEnd.OperationDir.Operation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AccOperationHistory extends JFrame implements Observer{
    AccOperationHistory thisobj;
    JTable table;
    JScrollPane scrollPane;
    JButton BackButton;
    JButton CreateNew;
    JButton Delete;
    JButton Edit;
    JPanel BottomButtons;
    ModifyOperationWindow modifyOperationWindow;
    DefaultTableModel model;
    Account account;
    AccountsWin previousWin;
    void reloadBalance(){
        int length = model.getRowCount();
        for (int i = 0 ; i<length ; i++){
            Operation op;
            try {
                op = account.getOperation(Integer.parseInt((String) model.getValueAt(i,0)));
            } catch (Exception exception) {
                exception.printStackTrace();
                new CommunicationWindow("Utracono synchronizacje pomiędzy GUI, a danymi");
                previousWin.setVisible(true);
                thisobj.dispose();
                return;
            }
            model.setValueAt(String.format("%.2f",op.getBalance()),i,5);
            previousWin.update(1);
        }
    }

    void addOperation(String data[]){
        model.addRow(data);
    }

    void reloadOperation(int rowID){
        Operation op;
        try {
            op = account.getOperation(Integer.parseInt((String) model.getValueAt(rowID,0)));
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        model.setValueAt(op.getPriority(),rowID,1);
        model.setValueAt(new SimpleDateFormat("dd.MM.yyyy").format(op.getDay()),rowID,2);
        model.setValueAt(op.getTag(),rowID,3);
        model.setValueAt(op.getValue(),rowID,4);
        model.setValueAt(op.getBalance(),rowID,5);
        model.setValueAt(op.getDescription(),rowID,6);

    }

    AccOperationHistory(Account account, AccountsWin previousWin){
        thisobj = this;
        this.previousWin = previousWin;
        this.account = account;
        setSize(1000,700);
        setMinimumSize(new Dimension(450,300));
        setLocationRelativeTo(previousWin);
        setTitle("Historia Operacji");
        setLayout(new BorderLayout(10,10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        BottomButtons = new JPanel();
        BottomButtons.setPreferredSize(new Dimension(980,40));

        String[] columnName = {"ID","Priorytet", "Dzień", "Tag", "Wartość", "Bilans", "Opis"};
        String[][] data = account.getData();
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(960,620));
        model =new javax.swing.table.DefaultTableModel (data, columnName);
        table = new JTable(model){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        {
            table.getColumnModel().getColumn(0).setMaxWidth(50);
            table.getColumnModel().getColumn(0).setMinWidth(50);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(1).setMaxWidth(70);
            table.getColumnModel().getColumn(1).setMinWidth(70);
            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(2).setMaxWidth(1000);
            table.getColumnModel().getColumn(2).setMinWidth(90);
            table.getColumnModel().getColumn(3).setPreferredWidth(150);
            table.getColumnModel().getColumn(3).setMaxWidth(200);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(4).setMaxWidth(100);
            table.getColumnModel().getColumn(4).setMinWidth(100);
            table.getColumnModel().getColumn(5).setMaxWidth(100);
            table.getColumnModel().getColumn(5).setMinWidth(100);
            table.getColumnModel().getColumn(6).setPreferredWidth(440);
        }

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sorter.setSortKeys(sortKeys);
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
                if (modifyOperationWindow == null)
                    modifyOperationWindow = new ModifyOperationWindow(account,thisobj,-1,0);
            }
        });
        BottomButtons.add(CreateNew);

        Delete = new JButton("Usuń");
        Delete.setSize(new Dimension(200,20));
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] chosenRows = table.getSelectedRows();
                for (int i = chosenRows.length-1; i >= 0; i--){
                    int index = Integer.parseInt((String) table.getValueAt(chosenRows[i],0));
                    Operation toDelete;
                    try {
                        toDelete = account.getOperation(index);
                    }
                    catch(Exception ex){
                        new CommunicationWindow(ex.getMessage());
                        return;
                    }
                    model.removeRow(table.getRowSorter().convertRowIndexToModel(chosenRows[i]));
                    account.deleteOperation(index);
                }
                sorter.sort();
                reloadBalance();
            }
        });
        BottomButtons.add(Delete);

        Edit = new JButton("Edytuj");
        Edit.setSize( 200,20);
        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modifyOperationWindow == null && table.getSelectedRow()!= -1)
                    modifyOperationWindow = new ModifyOperationWindow(
                            account,
                            thisobj,
                            Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0)),
                            table.getSelectedRow());
                }
        });
        BottomButtons.add(Edit);
        add(BottomButtons, BorderLayout.PAGE_END);
    }

    @Override
    public void update(int x) {
        modifyOperationWindow = null;
    }
}
