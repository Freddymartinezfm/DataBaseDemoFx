package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Artist {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;

	public SimpleIntegerProperty getId() {
		return id;
	}

	public void setId(int id) {
		new SimpleIntegerProperty(id);
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(String name) {
		new SimpleStringProperty(name);

	}
}
