package com.campin.DB;

import com.campin.Utils.PlannedTrip;
import com.campin.Utils.User;
import com.campin.Utils.Trip;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by noam on 03/06/2017.
 * Updated by Danielle on 25/07/2017
 */

public class Model {

    private static Model instance;
    private ModelFireBase remote;
    private ModelSql local;

    private List<Trip> tripsData = new LinkedList<Trip>();
    private List<PlannedTrip> plannedTripData = new LinkedList<PlannedTrip>();

    private static int currentKeyPlanned;
    private static int currentKeyTrips;
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

    public List<Trip> getTripsData() {
        return tripsData;
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

    public interface GetAllTripsListener{
        void onComplete(List<Trip> tripsList, int currentMaxKey);
        void onCancel();
    }

    public interface GetPlannedTripListener{
        void onComplete(PlannedTrip plannedTrip);
        public void onCancel();
    }

    public interface GetUserByIdListener{
        void onComplete(User user);
        public void onCancel();
    }

    public interface GetTripListener{
        void onComplete(Trip trip);
        public void onCancel();
    }

    public static void setCurrentKeyPlanned(int currentKey) {
        Model.currentKeyPlanned = currentKey;
    }

    public static int getCurrentKeyPlanned() {
        return currentKeyPlanned;
    }

    public static int getCurrentKeyTrips() {
        return currentKeyTrips;
    }

    public static void setCurrentKeyTrips(int currentKeyTrips) {
        Model.currentKeyTrips = currentKeyTrips;
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

    public void addTrip(Trip trip, SuccessListener listener){
        remote.addTrip(trip, listener);
    }

    public void addPlannedTrip(final PlannedTrip plannedTrip, final SuccessListener listener){

            // Save the dessert to firebase database
            remote.addPlannedTrip(plannedTrip, listener);

            // Update the key
            setCurrentKeyPlanned(getCurrentKeyPlanned() + 1);
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

    public Trip getTripById(String id, Model.GetTripListener listener){
        return null; //remote.getTripById(id,listener);
    }

    public void getAllTripAsynch(final GetAllTripsListener listener) {
        // Get last update date
        final double lastUpdateDate = TripSql.getLastUpdateDate(local.getReadbleDB());

        // Get all trips records that where updated since last update date
        remote.getTripsFromDate(lastUpdateDate, new GetAllTripsListener() {
            @Override
            public void onComplete(List<Trip> tripsList, int currentMaxKey) {
                if (tripsList != null && tripsList.size() > 0) {

                    // Update the local db
                    double recentUpdate = lastUpdateDate;
                    for (Trip trip : tripsList) {
                        // If new trip
                        if (local.getTripById(trip.getTripID()) == null) {
                            TripSql.addTrip(local.getWritableDB(), trip);
                        }
                        //If this update
                        else {
                            //TripSql.updateTrip(local.getWritableDB(), trip);
                        }

                        if (trip.getLastUpdated() > recentUpdate) {
                            recentUpdate = trip.getLastUpdated();
                        }
                    }

                    // Update the last update date
                    TripSql.setLastUpdateDate(local.getWritableDB(), recentUpdate);

                    // Update the current key
                    if (getCurrentKeyTrips() <= currentMaxKey) {
                        setCurrentKeyTrips(currentMaxKey + 1);
                    }
                }

                // Return all Desserts from the updated local db
                List<Trip> result = TripSql.getAllTrips(local.getReadbleDB());
                listener.onComplete(result, currentMaxKey);
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
