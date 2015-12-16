/**
 * Created by peterzohdy on 16/12/2015.
 */
public interface Inputforms {

    //all inputform classes should implement these methods
    void submitButtonPressed();
    void initializeScene();
    void show();
    void setAlert(String titleText,String headerText);
    void close();
}
