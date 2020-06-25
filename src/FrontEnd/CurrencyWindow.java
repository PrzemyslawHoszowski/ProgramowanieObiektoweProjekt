package FrontEnd;

import BackEnd.HomeBalance;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class CurrencyWindow extends JFrame {
    JTable table;
    JScrollPane scrollPane;
    DefaultTableModel model;
    HomeBalance homeBalance;
    MainWindow previousWin;
    JButton backButton;
    JButton addNew;
    JButton delete;
    JButton update;
    JPanel Buttons;
    CurrencyWindow thisObj;
    AddCurrency addCurrency;
    int addCurrencyExitCode;

    CurrencyWindow(MainWindow previousWin, HomeBalance homeBalance) {
        thisObj = this;
        this.previousWin = previousWin;
        this.homeBalance = homeBalance;
        setPreferredSize(new Dimension(570, 500));
        setMinimumSize(new Dimension(570, 400));
        setLocationRelativeTo(previousWin);
        setTitle("Okno Walut");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ///TODO zamknięcie okna i przejscie do poprzedniego??

        String[] columName = {"ID", "Nazwa", "Kurs", "Ostatnia Aktualizacja", "Ilość pieniędzy"};
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(570, 320));
        model = new DefaultTableModel(homeBalance.getCurrencyData(), columName);
        table = new JTable(model) {
            public boolean editCellAt(int row, int column, EventObject e) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        int tableComponentSize[] = {50, 100, 100, 130, 180};
        for (int i = 0; i < 5; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            if (i == 3) continue;
            table.getColumnModel().getColumn(i).setMaxWidth(tableComponentSize[i]);
            table.getColumnModel().getColumn(i).setMinWidth(tableComponentSize[i]);

        }
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sorter.setSortKeys(sortKeys);
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.PAGE_START);

        Buttons = new JPanel();
        Buttons.setPreferredSize(new Dimension(570, 30));
        backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousWin.setLocationRelativeTo(thisObj);
                previousWin.setVisible(true);
                if (addCurrency != null) addCurrency.dispose();
                dispose();
            }
        });

        addNew = new JButton("Dodaj");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addCurrency == null) {
                    addCurrency = new AddCurrency(thisObj, homeBalance);
                }
            }
        });

        delete = new JButton("Usun");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tag = table.getValueAt(table.getSelectedRow(), 1).toString();
                if (homeBalance.findAccountWith(tag)) {
                    new CommunicationWindow("Przed usunięciem konta proszę usunąc konta korzystające z tej waluty");
                    return;
                }
                try {
                    homeBalance.deleteCurrency(tag);
                    model.removeRow(table.getRowSorter().convertRowIndexToModel(table.getSelectedRow()));
                } catch (Exception exception) {
                    new CommunicationWindow(exception.getMessage());
                }
            }
        });

        update = new JButton("Zaktualizuj kursy");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeBalance.updateCurrency();
                new CurrencyWindow(previousWin, homeBalance);
                dispose();
            }
        });


        Buttons.add(backButton);
        Buttons.add(addNew);
        Buttons.add(delete);
        Buttons.add(update);
        add(Buttons, BorderLayout.CENTER);
        add(previousWin.emptySpace(), BorderLayout.PAGE_END);
        setVisible(true);
    }

    public void update(int code) {
        if (code == 1) {
            model.addRow(homeBalance.getLastCurrencyData());
        }
        addCurrency = null;
    }
}
