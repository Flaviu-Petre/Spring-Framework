package com.glo;

public class Client {
    //region Fields
    private int clientId;
    private String clientName;
    //endregion

    //region Constructors
    public Client(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }
    //endregion

    //region Getters

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    //endregion

    //region Setters

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    //endregion
}
