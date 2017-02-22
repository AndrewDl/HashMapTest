import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrew on 23.02.2017.
 */
public class MyClassTest {

    @Test
    public void MyClass_CompareIDofInstances_false(){
        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");

        Assert.assertEquals("Compare IDs. Must be different", false, myObject1.getID()==myObject2.getID());
    }

    @Test
    public void MyClass_AssignParent_SuccessTrue(){
        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");

        myObject1.setParent(myObject2);

        boolean isParent = myObject2.getID() == myObject1.getParentID();
        Assert.assertEquals("Check if parent is assigned.", true, isParent);

        Assert.assertEquals("Check object's parentID, must be 0", 0, myObject2.getParentID());
    }
}
