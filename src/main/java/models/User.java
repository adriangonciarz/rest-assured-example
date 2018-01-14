package models;

public class User {
    public Integer id;
    public String firstName;
    public String lastName;
    public Integer age;

    public User(){
    }

    public User(String fName, String lName, Integer age){
        this.firstName = fName;
        this.lastName = lName;
        this.age = age;
    }
}
