package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import sample.model.Album;
import sample.model.Artist;
import sample.model.Datasource;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	@FXML
	private TableView<Artist> artistTable;

	private List<Artist> list;

	public void listArtists() {
		Task<ObservableList<Artist>> task = new GetAllArtistsTask();
		artistTable.itemsProperty().bind(task.valueProperty());
		new Thread(task).start();
	}
}




class GetAllArtistsTask extends Task {

	@Override
	public ObservableList<Artist> call() {

		return FXCollections.observableArrayList
				(Datasource.getInstance().queryArtists(Datasource.ORDER_BY_DESC));
		}

}
