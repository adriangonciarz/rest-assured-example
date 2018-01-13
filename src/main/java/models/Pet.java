package models;

public class Pet {
    public Integer id;
    public Integer user_id;
    public String name;
    public Integer age;

    public Pet(){}

    public Pet(Integer userId, String name, Integer age){
        this.user_id = userId;
        this.name = name;
        this.age = age;
    }
}
