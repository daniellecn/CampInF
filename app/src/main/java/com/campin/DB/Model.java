package com.campin.DB;

import com.campin.Utils.PlannedTrip;
import com.campin.Utils.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by noam on 03/06/2017.
 */

public class Model {

    private static Model instance;


    private List<PlannedTrip> plannedTripData = new LinkedList<PlannedTrip>();

    private ModelFireBase remote;
    private ModelSql local;

    private static int currentKey;

    private static User connectedUser;

    public static Model instance(){

        if (instance == null) {
            instance = new Model();
        }

        return  instance;
    }

    private Model() {

        remote = new ModelFireBase();
        local = new ModelSql();
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        Model.connectedUser = connectedUser;
    }

    public interface LogInListener {
        void onComplete(boolean isLogIn);
    }

    public interface SignUpListener{
        void onComplete(boolean isExist);
    }

    public interface SuccessListener {
        public void onResult(boolean result);
    }

    public interface SaveImageListener{
        public void fail();
        public void complete(String url);
    }


    public interface GetAllPlannedTripListener{
        void onComplete(List<PlannedTrip> plannedTripList, int currentMaxKey);
        public void onCancel();
    }

    public interface GetPlannedTripListener{
        void onComplete(PlannedTrip plannedTrip);
        public void onCancel();
    }

    public interface GetUserByIdListener{
        void onComplete(User user);
        public void onCancel();
    }

    public static void setCurrentKey(int currentKey) {
        Model.currentKey = currentKey;
    }

    public static int getCurrentKey() {
        return currentKey;
    }

    public List<PlannedTrip> getPlannedTripData() {
        return plannedTripData;
    }

    public void logIn(User user, LogInListener listener){
        remote.userLogIn(user, listener);
    }

    public void signUp(final User user, final SuccessListener listener){
        remote.userSignUp(user, listener);
    }

    public void addPlannedTrip(final PlannedTrip plannedTrip, final SuccessListener listener){

            // Save the dessert to firebase database
            remote.addPlannedTrip(plannedTrip, listener);

            // Update the key
            setCurrentKey(getCurrentKey() + 1);
    }

    public void updateDessert(PlannedTrip plannedTrip, Model.SuccessListener listener){
        remote.addPlannedTrip(plannedTrip, listener);
    }


    public PlannedTrip getDessertById(int id){
        return local.getPlannedTripById(id);
    }

    public void isUserExist(String userId, Model.SuccessListener listener){
        remote.isUserExist(userId, listener);
    }

    public void isUserMailExist(String userMail, Model.SuccessListener listener){
       // remote.isUserMailExist(userMail, listener);
    }

    public List<PlannedTrip> getBySearch(String query){
        return local.getBySearch(query);
    }

    public User getUserById(String id, Model.GetUserByIdListener listener){
        return remote.getUserById(id,listener);
    }
}
