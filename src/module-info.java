module DatabaseDemoFx {
	requires javafx.fxml;
	requires java.sql;
	requires javafx.controls;
	requires org.apache.logging.log4j;

	opens sample;
	opens sample.model;
}