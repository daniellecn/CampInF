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

    public interface GetAllDessertsAsynchListener{
        void onComplete(List<PlannedTrip> plannedTripList);
        public void onCancel();
    }

    public interface GetPlannedTripListener{
        void onComplete(PlannedTrip plannedTrip);
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

    public void getAllPlannedTripAsynch(final GetAllDessertsAsynchListener listener){
        // Get last update date
        final double lastUpdateDate = PlannedTripSql.getLastUpdateDate(local.getReadbleDB());

        // Get all desserts records that where updated since last update date
        /*remote.getDessertsFromDate(lastUpdateDate, new GetAllPlannedTripListener() {
            @Override
            public void onComplete(List<PlannedTrip> plannedTripList, int currentMaxKey) {
                // If there are new desserts in firebase
                if (plannedTripList != null && plannedTripList.size() > 0){

                    // Update the local db
                    double recentUpdate = lastUpdateDate;
                    for (PlannedTrip plannedTrip : plannedTripList){
                        // If new dessert
                        if (local.getPlannedTripById(plannedTrip.getTripId()) == null){
                            PlannedTripSql.addTrip(local.getWritableDB(), plannedTrip);
                        }
                        // If this update
                        else{
                            PlannedTripSql.updateDessert(local.getWritableDB(), plannedTrip);
                        }

                        if (plannedTrip.getLastUpdated() > recentUpdate){
                            recentUpdate = plannedTrip.getLastUpdated();
                        }
                    }

                    // Update the last update date
                    DessertSql.setLastUpdateDate(local.getWritableDB(), recentUpdate);

                    // Update the current key
                    if (getCurrentKey() <= currentMaxKey){
                        setCurrentKey(currentMaxKey + 1);
                    }
                }

                // Return all Desserts from the updated local db
                List<PlannedTrip> result = PlannedTripSql.getAllDesserts(local.getReadbleDB());
                listener.onComplete(result);
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }
        });*/
    }

    public PlannedTrip getDessertById(int id){
        return local.getPlannedTripById(id);
    }

    public void isUserNameExist(String userName, Model.SuccessListener listener){
        remote.isUserNameExist(userName, listener);
    }

    public void isUserMailExist(String userMail, Model.SuccessListener listener){
       // remote.isUserMailExist(userMail, listener);
    }

    public List<PlannedTrip> getBySearch(String query){
        return local.getBySearch(query);
    }
}
