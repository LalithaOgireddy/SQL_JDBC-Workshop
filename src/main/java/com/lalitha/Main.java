package com.lalitha;

import com.lalitha.dao.CityDao;
import com.lalitha.dao.CityDaoJDBC;
import com.lalitha.model.City;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        City newCity = new City("Hyderabad","NLD","RR",1234567);
        CityDao cityDao = new CityDaoJDBC();
        System.out.println(cityDao.findById(5));

        System.out.println(cityDao.findByCode("NLD"));
        System.out.println(cityDao.add(newCity));
    }
}