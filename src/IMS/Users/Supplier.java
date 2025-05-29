package IMS.Users;

public class Supplier extends User{
    private String department;

    public Supplier(String UserID, String name, String address, String email, String department) {
        super(UserID, name, address, email);
        this.department = department;
    }

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }

}
