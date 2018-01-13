package models;

public class User {
    public Integer id;
    public String first_name;
    public String last_name;
    public Integer age;

    public User(){
    }

    public User(String fName, String lName, Integer age){
        this.first_name = fName;
        this.last_name = lName;
        this.age = age;
    }
}
