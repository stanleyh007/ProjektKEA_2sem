/**
 * Created by Stormwind on 14/12/2015.
 */
public class Admin implements SysAdmin {
    private String password;
    private String userName;

    public Admin(String password, String userName)
    {
        this.password = password;
        this.userName = userName;
    }

    @Override
    public String getPassWord() {
        return this.password;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }
}
