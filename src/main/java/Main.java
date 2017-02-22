import java.io.IOException;
import java.util.*;

/**
 * Created by Andrew on 22.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        XMLwriterReader<HashMap> xmLwriterReader = new XMLwriterReader<HashMap>("src/text.xml");

        HashMap<Long,MyClass> smthingStrange = new HashMap<Long, MyClass>();

        MyClass myObject1 = new MyClass("Object1");
        MyClass myObject2 = new MyClass("Object2");
        MyClass myObject3 = new MyClass("Object3");
        MyClass myObject4 = new MyClass("Object4");
        MyClass myObject5 = new MyClass("Object5");

        myObject1.setParent(myObject2);
        myObject2.setParent(myObject3);
        myObject3.setParent(myObject4);


/*
        for (int i = 0; i < 10000 ; i++) {
            MyClass myObject = new MyClass(Integer.toString(i));
            smthingStrange.put(myObject.getID(),myObject);
        }*/

        smthingStrange.put(myObject1.getID(),myObject1);
        smthingStrange.put(myObject2.getID(),myObject2);
        smthingStrange.put(myObject3.getID(),myObject3);
        smthingStrange.put(myObject4.getID(),myObject4);
        smthingStrange.put(myObject5.getID(),myObject5);

        try {
            xmLwriterReader.WriteFile(smthingStrange, smthingStrange.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //цей список міститиме списки об'єктів першого покоління та їх предків
        List<LinkedList<MyClass>> GenerationOneList = mapDependencies(smthingStrange);

        long t1 = new Date().getTime();
        //створюємо список об'єктів 1го покоління (кореневі)

        long t2 = new Date().getTime();

        System.out.println("Finished in: " + (t2-t1));

        System.out.println(GenerationOneList.size());
    }

    /**
     * Метод шукає об'єкт з вказаним ID в Мар
     * @param ID ідентифікатор об'єкта для пошуку
     * @param map Мап, в якому шукати
     * @return екземпляр класу. null якшо не знайшов
     */
    public static MyClass getObjectByID(Long ID, Map<Long,MyClass> map){
        for (Map.Entry<Long,MyClass> obj : map.entrySet()){
            if (obj.getKey().longValue()==ID) {
                return obj.getValue();
            }
        }

        return null;
    }

    /**
     * Метод вважає перший елемент списку кореневим та додає в цей же список всі його предки по ієрархії
     * @param list список для додавання
     * @param map Мар з якого беремо значення
     */
    public static void fillParents(LinkedList<MyClass> list, Map<Long,MyClass> map){

        for (int i = 0; i < list.size() ; i++) {
            long ParentID = list.get(i).getParentID();
            if (ParentID != 0) {
                MyClass object = getObjectByID(ParentID, map);
                list.add(object);
            }
        }

    }

    /**
     * Шукає в Мар об'єкти і формує з них список
     * Під кожен елемент першого покоління формується новий вкладений список, що містить цей об'єкт 1го покоління та його предки
     * @param map Мар з яйого дістаємо цей список залежностей
     * @return список де кожен елемент є списком, що містить елемент 1го покоління та його предки
     */
    public static List<LinkedList<MyClass>> mapDependencies(Map<Long,MyClass> map){
        //створюємо список об'єктів 1го покоління (кореневі)
        List<LinkedList<MyClass>> list = new LinkedList<LinkedList<MyClass>>();
        for (Map.Entry<Long,MyClass> obj : map.entrySet()){
            if (obj.getValue().getGeneration()==1) {
                //якщо було знайдено об'єкт першого покоління - створюємо вкладений список
                LinkedList<MyClass> innerList = new LinkedList<MyClass>();
                //додаємо в цей список об'ект першого покоління
                innerList.add(obj.getValue());
                //шукаємо всіх предків об'єкту 1го покоління та формуємо з них список однорівневий
                fillParents(innerList,map);
                //додаємо однорівневий список в список першого покоління
                list.add(innerList);
            }
        }
        return list;
    }
}
