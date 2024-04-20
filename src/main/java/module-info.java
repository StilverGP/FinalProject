module com.github.StilverGP {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens com.github.StilverGP to javafx.fxml;
    opens com.github.StilverGP.model.connection to java.xml.bind;

    exports com.github.StilverGP;
    exports com.github.StilverGP.view;
    opens com.github.StilverGP.view to javafx.fxml;
}
