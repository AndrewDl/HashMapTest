import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by Andrew on 23.02.2017.
 */
public class MainTest {

    @Test
    public void TestNumberOfFirstGenObjects_5Objects3Parents_2FirstGenExpected(){
        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");
        MyClass myObject3 = new MyClass("Object3");
        MyClass myObject4 = new MyClass("Object4");
        MyClass myObject5 = new MyClass("Object5");

        myObject1.setParent(myObject2);
        myObject2.setParent(myObject3);
        myObject3.setParent(myObject4);

        HashMap<Long,MyClass> objectMap = new HashMap<Long, MyClass>();

        objectMap.put(myObject1.getID(),myObject1);
        objectMap.put(myObject2.getID(),myObject2);
        objectMap.put(myObject3.getID(),myObject3);
        objectMap.put(myObject4.getID(),myObject4);
        objectMap.put(myObject5.getID(),myObject5);

        //цей список міститиме списки об'єктів першого покоління та їх предків
        List<LinkedList<MyClass>> GenerationOneList = Main.mapDependencies(objectMap);

        Assert.assertEquals("Expect 2 first gen ogjects",2,GenerationOneList.size());
    }

    @Test
    public void TestNumberOfFirstGenObjects_5Objects0Parents_5FirstGenExpected(){
        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");
        MyClass myObject3 = new MyClass("Object3");
        MyClass myObject4 = new MyClass("Object4");
        MyClass myObject5 = new MyClass("Object5");

        HashMap<Long,MyClass> objectMap = new HashMap<Long, MyClass>();

        objectMap.put(myObject1.getID(),myObject1);
        objectMap.put(myObject2.getID(),myObject2);
        objectMap.put(myObject3.getID(),myObject3);
        objectMap.put(myObject4.getID(),myObject4);
        objectMap.put(myObject5.getID(),myObject5);

        //цей список міститиме списки об'єктів першого покоління та їх предків
        List<LinkedList<MyClass>> GenerationOneList = Main.mapDependencies(objectMap);

        Assert.assertEquals("Expect 2 first gen ogjects",5,GenerationOneList.size());
    }

    @Test
    public void TestTreeOfObjects_5Objects2and1parent_allTrue(){
        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");
        MyClass myObject3 = new MyClass("Object3");
        MyClass myObject4 = new MyClass("Object4");
        MyClass myObject5 = new MyClass("Object5");

        myObject1.setParent(myObject2);
        myObject2.setParent(myObject3);

        myObject4.setParent(myObject5);

        HashMap<Long,MyClass> objectMap = new HashMap<Long, MyClass>();

        objectMap.put(myObject1.getID(),myObject1);
        objectMap.put(myObject2.getID(),myObject2);
        objectMap.put(myObject3.getID(),myObject3);
        objectMap.put(myObject4.getID(),myObject4);
        objectMap.put(myObject5.getID(),myObject5);

        //цей список міститиме списки об'єктів першого покоління та їх предків
        List<LinkedList<MyClass>> GenerationOneList = Main.mapDependencies(objectMap);

        Assert.assertEquals("Expect 2 first gen objects",2,GenerationOneList.size());
        Assert.assertEquals("Test that GenerationOneList contains correct hierarchy",true,TestTreeOfObjects(GenerationOneList));
    }

    @Test
    public void TestTreeOfObjects_9Objectst_allTrue(){
        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");
        MyClass myObject3 = new MyClass("Object3");
        MyClass myObject4 = new MyClass("Object4");
        MyClass myObject5 = new MyClass("Object5");
        MyClass myObject6 = new MyClass("Object6");
        MyClass myObject7 = new MyClass("Object7");
        MyClass myObject8 = new MyClass("Object8");
        MyClass myObject9 = new MyClass("Object9");

        myObject1.setParent(myObject2);
        myObject2.setParent(myObject3);

        myObject4.setParent(myObject5);
        myObject5.setParent(myObject9);

        HashMap<Long,MyClass> objectMap = new HashMap<Long, MyClass>();

        objectMap.put(myObject1.getID(),myObject1);
        objectMap.put(myObject2.getID(),myObject2);
        objectMap.put(myObject3.getID(),myObject3);
        objectMap.put(myObject4.getID(),myObject4);
        objectMap.put(myObject5.getID(),myObject5);

        objectMap.put(myObject6.getID(),myObject6);
        objectMap.put(myObject7.getID(),myObject7);
        objectMap.put(myObject8.getID(),myObject8);
        objectMap.put(myObject9.getID(),myObject9);

        //цей список міститиме списки об'єктів першого покоління та їх предків
        List<LinkedList<MyClass>> GenerationOneList = Main.mapDependencies(objectMap);

        Assert.assertEquals("Expect 5 first gen objects",5,GenerationOneList.size());
        Assert.assertEquals("Test that GenerationOneList contains correct hierarchy",true,TestTreeOfObjects(GenerationOneList));
    }

    /**
     * Перевіряє чи вірна ієрархія по спискам з предками
     * @param GenerationOneList
     * @return
     */
    public boolean TestTreeOfObjects(List<LinkedList<MyClass>> GenerationOneList){
        boolean totalResult = true;
        for (LinkedList<MyClass> innerList : GenerationOneList) {
            //Для кожного елементу в списку перевіряємо чи міститься в списку його предок
            for (int i = 0; i < innerList.size(); i++) {

                if (innerList.get(i).getParentID() != 0) {
                    //якщо всі предки для всіх елементів містяться в усіх списках - повернути true
                    //якщо хоч один - false результат - false
                    totalResult &= containsObjectWithID(innerList.get(i).getParentID(), innerList);
                }
            }
        }
        return totalResult;
    }

    /**
     * Перевіряє чи міститься в списку елемент з заданим значенням поля ID
     * @param ID значення по якому шукається елемент в списку
     * @param list в якому списку шукати
     * @return true - якщо елемент міститься в списку.
     */
    public boolean containsObjectWithID(long ID, LinkedList<MyClass> list){
        boolean result = false;
        if (ID!=0) {

            for (MyClass obj : list) {
                result |= obj.getID() == ID;
            }
        }
        return result;
    }
}
