package ntnu.idatt1002.dao;

import java.io.*;

/**
 * Generic default class for common methods in the DAO package
 */
final class GenericDAO {
    /**
     * Write object to storage
     * @param obj Class that implements serializable
     * @param filepath filepath starting with "src/"
     */
    static void serialize(Object obj, String filepath){
        File file = new File(filepath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Read object from storage, ready to be cast as it's original class
     * @param filepath filepath starting with "src/"
     * @return {@code Object} instance read from file
     */
    static Object deserialize(String filepath){
        File file = new File(filepath);
        Object obj = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            obj = ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return obj;
    }
}
