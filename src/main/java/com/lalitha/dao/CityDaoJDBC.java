package com.lalitha.dao;

import com.lalitha.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.lalitha.db.MySQLConnection.getConnection;

public class CityDaoJDBC implements CityDao {


    @Override
    public City findById(int id) {
        String sql = "SELECT * FROM city WHERE id=?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int id1 = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String countryCode = resultSet.getString("countryCode");
                    String district = resultSet.getString("district");
                    int population = resultSet.getInt("population");
                    return new City(id1,name,countryCode,district,population);
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<City> findByCode(String code) {
        String sql = "SELECT * FROM city WHERE countryCode =?";
        List<City> cities = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1,code);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int id1 = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String countryCode = resultSet.getString("countryCode");
                    String district = resultSet.getString("district");
                    int population = resultSet.getInt("population");
                    cities.add(new City(id1,name,countryCode,district,population));
                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public List<City> findByName(String name) {
        String sql = "SELECT * FROM city WHERE name =?";
        List<City> cities = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1,name);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name1 = resultSet.getString("name");
                    String countryCode = resultSet.getString("countryCode");
                    String district = resultSet.getString("district");
                    int population = resultSet.getInt("population");
                    cities.add(new City(id,name1,countryCode,district,population));
                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public List<City> findAll() {
        String sql="select * from city";
        List<City> cities=new ArrayList<>();
        try(Connection connection=getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
        ) {
            while(resultSet.next()){
                int id1=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String countryCode=resultSet.getString("countryCode");
                String district=resultSet.getString("district");
                int population=resultSet.getInt("population");
                cities.add(new City(id1,name,countryCode,district,population));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public City add(City city) {
        String sql = "INSERT INTO city(name,countryCode,district,population) VALUES(?,?,?,?)";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);)
        {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountrycode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4,city.getPopulation());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0 ){
                System.out.println("City is added!");
            }

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    int generatedId = generatedKeys.getInt(1);
                    city.setId(generatedId);
                    return city;
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City update(City city) {
        String sql = "UPDATE city SET name = ?, countryCode = ?, district =?,population = ? WHERE id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);)
        {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountrycode());
            preparedStatement.setString(3, city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.setInt(5,city.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected == 1) System.out.println("City updated");

            return city;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(City city) {
        String sql = "DELETE FROM city WHERE id=?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);)
        {
            preparedStatement.setInt(1,city.getId());
            return preparedStatement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
