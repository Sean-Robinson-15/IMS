package IMS.Users;

public class User {
    private final String userID;
    private String name;
    private String address;
    private String email;

    public User(String userID, String name, String address, String email) {
        this.userID = userID.toUpperCase();
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getID(){
        return userID;
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
        if (!name.isEmpty()) {this.name = name;}
    }
    public void setEmail(String email){
        if (!email.isEmpty()) {this.email = email;}
    }
    public void setAddress(String address){
         if (!address.isEmpty()) {this.address=address;}
    }
}
