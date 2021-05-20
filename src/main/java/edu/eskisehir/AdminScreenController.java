package edu.eskisehir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {
    public TableView<Barber> barbersTable;
    public TableColumn<Barber,String> nameCol;
    public TableColumn<Barber,Integer> salaryCol;
    public TableColumn<Barber,String> surnameCol;

    DataBaseOperations db = new DataBaseOperations();

    public void nameChange(TableColumn.CellEditEvent<Barber, String> barberStringCellEditEvent) {
        Barber barber = barbersTable.getSelectionModel().getSelectedItem();
        barber.setName(barberStringCellEditEvent.getNewValue());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editableCols();
        loadData();
    }

    public void editableCols(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e ->{
            Barber barber = e.getTableView().getItems().get(e.getTablePosition().getRow());
            barber.setName(e.getNewValue());
            db.updateBarber(Attribute.NAME,e.getNewValue(), barber.getId());
        });

        barbersTable.setEditable(true);
    }

    private void loadData(){
        ObservableList<Barber> dataTable = FXCollections.observableArrayList();
        List<Barber> barbers = db.getBarbers();
        for (Barber barber : barbers) {
            dataTable.add(barber);
        }
        barbersTable.setItems(dataTable);
    }





























}
