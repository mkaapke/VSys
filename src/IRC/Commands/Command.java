package IRC.Commands;

public class Command {

    private String name; //Name des Commands
    private String[] parameters; //Jeder Command hat mindestens einen Parameter
    private Integer size; //Anzahl der Paramether
    private boolean noMessage; //Es gibt Befehle mit einer Message und welche ohne.


    public Command(String name, Integer size, boolean noMessage) {
        this.name = name;
        this.size = size;
        this.parameters = new String[size];
        this.noMessage = noMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isNoMessage() {
        return noMessage;
    }

    public void setNoMessage(boolean noMessage) {
        this.noMessage = noMessage;
    }
}
