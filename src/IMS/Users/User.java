package IMS.Users;

public class User {
    private final String UserID;
    private String name;
    private String address;
    private String email;

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
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
         this.address=address;
    }
}
