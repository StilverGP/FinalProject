package com.github.StilverGP.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    MAIN("view/main.fxml"),
    LOGIN("view/login.fxml"),
    FORMSIGNIN("view/formSignIn.fxml"),
    FORMROOM("view/formRoom.fxml"),
    FORMDELETEROOM("view/formDelRoom.fxml"),
    FORMBOOK("view/formBook.fxml"),
    FORMDELETEBOOK("view/formDeleteBook.fxml"),
    UPDATEUSERUSERNAME("view/updUserUsername.fxml"),
    UPDATEUSERNAME("view/updUserName.fxml"),
    UPDATEUSERMAIL("view/updUserMail.fxml"),
    MYBOOKS("view/myBooks.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
