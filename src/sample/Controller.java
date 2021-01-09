package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import sample.model.Album;
import sample.model.Artist;
import sample.model.Datasource;

import java.util.List;

public class Controller {
	@FXML
	private TableView artistTable;
	@FXML
	private ProgressBar progressBar;
	private List<Artist> list;
	@FXML
	public void listArtists() {
		Task<ObservableList<Artist>> task = new GetAllArtistsTask();
		artistTable.itemsProperty().bind(task.valueProperty());
		progressBar.progressProperty().bind(task.progressProperty());
		progressBar.setVisible(true);
		task.setOnSucceeded(e -> progressBar.setVisible(false));
		task.setOnFailed(e -> progressBar.setVisible(false));
		new Thread(task).start();
	}
	@FXML
	public void updateArtist(){
		// normally would be the selected artist, this is just a hack to bypass getting the input, from a dialog/from user.
//		final Artist artist = (Artist) artistTable.getSelectionModel().getSelectedItem();
		final Artist artist = (Artist) artistTable.getItems().get(66);
		System.out.println(artist.getName());

		Task<Boolean> task = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				// TODO get newName from user
				return Datasource.getInstance().updateArtistName(artist.getId(), "AC/DC");
			}
		};
		task.setOnSucceeded(e -> {
			if (task.valueProperty().get()) {
				artist.setName("AC/DC");
				artistTable.refresh();
			}
		});
		new Thread(task).start();
	}
	@FXML
	public void listAlbumsForArtist(){
		final Artist artist = (Artist) artistTable.getSelectionModel().getSelectedItem();
		if (artist == null){
			// TODO create confirmation dialog
			System.out.println("No Artist selected. ");
			return;
		}
		Task<ObservableList<Album>> task = new Task<ObservableList<Album>>() {
			@Override
			protected ObservableList<Album> call() throws Exception {
				return FXCollections.observableArrayList(
						Datasource.getInstance().queryAlbumsByArtistId(artist.getId()));
			}

		};
		System.out.println(artist.getName());
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
