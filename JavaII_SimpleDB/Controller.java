package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    @FXML private TableView<ModelTable> table;
    @FXML private TextField txt_input;

    ObservableList<ModelTable> olist = FXCollections.observableArrayList();

    public void setData(String query) {
        //query = "Select * From user;";
        /* Get database connection */
        try {
            Connection con = DBConnector.getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while(res != null && res.next()) {
                /* Create a table model to store queried data from data base */
                ModelTable model = new ModelTable(res.getString("firstname"),
                        res.getString("lastname"));

                /* Populate new model to the observable list */
                olist.add(model);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void loadDB(ActionEvent event) {
        table.getItems().clear();

        String query = txt_input.getText();

        /* Prepare data from database */
        setData(query);

        /* Create table columns */
        TableColumn col_first = new TableColumn("First");
        TableColumn col_last = new TableColumn("Last");
        table.getColumns().addAll(col_first, col_last);

        /* Associate data with column */
        col_first.setCellValueFactory(new PropertyValueFactory<>("fname"));
        col_last.setCellValueFactory(new PropertyValueFactory<>("lname"));

        /* Add data inside table */
        table.setItems(olist);
    }
}
