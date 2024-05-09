module com.github.StilverGP {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires java.desktop;

    opens com.github.StilverGP to javafx.fxml;
    opens com.github.StilverGP.model.connection to java.xml.bind;

    exports com.github.StilverGP;
    exports com.github.StilverGP.view;
    exports com.github.StilverGP.model.entity;
    opens com.github.StilverGP.view to javafx.fxml;
}
