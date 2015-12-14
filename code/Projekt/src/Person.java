import java.io.Serializable;

/**
 * Created by Stormwind on 14/12/2015.
 */
public abstract class Person implements Serializable {

    protected String email;
    protected int phone;

    public Person(String email, int phone)
    {
        this.email = email;
        this.phone = phone;
    }
    public abstract String getEmail();

    public abstract void setEmail(String email);

    public abstract int getPhone();

    public abstract void setPhone(int phone);

}

