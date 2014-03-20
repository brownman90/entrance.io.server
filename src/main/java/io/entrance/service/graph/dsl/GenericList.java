
package io.entrance.service.graph.dsl;

import io.entrance.service.json.gson.GSON;

import java.util.ArrayList;
import java.util.List;

public class GenericList<T> {

    private List<T> list = new ArrayList<T>();

    public GenericList() {
        super();
    }

    public GenericList(List<T> list) {
        super();
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }
    
    public void addElement(T element) {
        list.add(element);
    }
    
    public String json() {
        return GSON.INSTANCE.gson().toJson(list);
    }

}
