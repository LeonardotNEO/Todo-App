package ntnu.idatt1002.dao;

import java.io.*;

/**
 * The class {@code GenericDAO} provides static methods for serializing and deserializing objects.
 * The access is set to default, so only classes within the 'dao' package can utilize it.
 * To function as a generic method provider the methods takes and gives {@link Object} instances.
 * Therefore the classes utilizing these methods needs to make sure that the objects handled are
 * serializable, and that the objects get cast to their correct classes after deserializing.
 */
final class GenericDAO {
    /**
     * Serialize an {@link Object} to the given filepath.
     * @param obj an object which implements {@link Serializable}.
     * @param filepath where the object will be stored and it's filename. Needs to start with "src/".
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
     * Returns an {@link Object} deserialized from the given filepath.
     * Make sure this gets cast to it's correct class.
     * @param filepath where the object is stored and it's filename. Needs to start with "src/".
     *                 The used filetype is ".ser".
     * @return an {@link Object}. Will be {@code null} if the filepath is not valid.
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
