package com.revature.database;

public interface DaoInterface<O> {

    void create(O t);

    O getById(int id);

    void getAll();

    boolean update(O t);
}