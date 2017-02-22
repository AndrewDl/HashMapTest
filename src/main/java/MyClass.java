import java.util.Random;
import java.util.UUID;

/**
 * Created by Andrew on 22.02.2017.
 */
public class MyClass {
    private long ID = new Random().nextLong();

    private long ParentID = 0;
    private int Generation = 1;
    private String Name = "";

    public MyClass(String name){
        Name = name;
    }

    public long getID(){
        return ID;
    }

    public long getParentID() {
        return ParentID;
    }

    private void setParentID(long parentID) {
        ParentID = parentID;
    }

    public void setParent(MyClass parent) {
        this.setParentID(parent.getID());
        parent.increaseGeneration();
    }

    public int getGeneration() {
        return Generation;
    }

    private void increaseGeneration() {
        if (Generation < 2){
            Generation++;
        }
    }

    public String getName() {
        return Name;
    }
}
