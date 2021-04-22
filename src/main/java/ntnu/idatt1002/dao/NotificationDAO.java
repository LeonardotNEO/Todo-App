package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;

import java.util.ArrayList;

public final class NotificationDAO {
    private static ArrayList<Notification> notifs = new ArrayList<>();

    static ArrayList<Notification> getNotifs(){
        return notifs;
    }

    static void setNotifs(ArrayList<Notification> newNotifs){
        notifs = newNotifs;
    }

    public static void add(ArrayList<Notification> newNotifs){
        notifs.addAll(newNotifs);
    }

    public static void add(Notification notif){
        notifs.add(notif);
    }

    public static ArrayList<Notification> list(){
        return notifs;
    }

    public static Notification get(long id){
        for(Notification notif : notifs){
            if(notif.getNotifId() == id){ return notif; }
        }
        return null;
    }

    public static void edit(String newUsername){
        for(Notification notif : notifs){
            notif.setUsername(newUsername);
        }
    }

    public static void delete(Notification notif){
        notifs.remove(notif);
    }
}
