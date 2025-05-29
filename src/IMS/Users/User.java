package IMS.Users;

public class User {
    private final String UserID;
    private final String name;
    private final String address;
    private final String email;

    public User(String UserID, String name, String address, String email) {
        this.UserID = UserID;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getID(){
        return UserID;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
}
