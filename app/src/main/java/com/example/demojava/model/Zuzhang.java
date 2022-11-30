package com.example.demojava.model;

import java.util.ArrayList;
import java.util.List;

public class Zuzhang {

    private List<Person> list=new ArrayList<>();

    public void addPerson(Person person){
        list.add(person);
    }

    public synchronized List<Person> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        for (Person person : list) {
            s.append(person.getName());
            s.append(",");
        }
        return s.toString();
    }
}
