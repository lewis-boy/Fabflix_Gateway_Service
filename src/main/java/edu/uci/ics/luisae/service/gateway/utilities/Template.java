package edu.uci.ics.luisae.service.gateway.utilities;

public class Template {
    private String name;
    private Object value;
    private Template(String name, Object value){
        this.name = name;
        this.value = value;
    }
    public static Template create(String name, Object value){
        return new Template(name, value);
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
