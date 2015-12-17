/**
 * Created by peterzohdy on 16/12/2015.
 */
public interface Inputforms {

    //all classes that implements this interface should have these methods in order to compile
    void submitButtonPressed();
    void initializeScene();
    void show();
    void setAlert(String titleText,String headerText);
    void close();
}
