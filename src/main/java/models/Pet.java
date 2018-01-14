package models;

public class Pet {
    public Integer id;
    public Integer userId;
    public String name;
    public Integer age;

    public Pet(){}

    public Pet(Integer userId, String name, Integer age){
        this.userId = userId;
        this.name = name;
        this.age = age;
    }
}
