package com.campin.DB;

import android.graphics.Bitmap;

import com.campin.Utils.Area;
import com.campin.Utils.PlannedTrip;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;
import com.campin.Utils.Trip;

import java.util.ArrayList;
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
    private static int currentKeyTripLevel;
    private static int currentKeyTripType;
    private static int currentKeyArea;
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

    public interface getTripsUserBelongsListener{
        void onComplete(ArrayList<PlannedTrip> plannedTripList, int currentMaxKey);
        public void onCancel();
    }

    public interface GetAllTripsListener{
        void onComplete(List<Trip> tripsList, int currentMaxKey);
        void onCancel();
    }

    public interface GetAllTripLevelsListener{
        void onComplete(List<TripLevel> tripsList, int currentMaxKey);
        void onCancel();
    }

    public interface GetAllTripTypesListener{
        void onComplete(List<TripType> tripsList, int currentMaxKey);
        void onCancel();
    }

    public interface GetAllAreaListener{
        void onComplete(List<Area> areaList, int currentMaxKey);
        void onCancel();
    }

    public interface GetUserByIdListener{
        void onComplete(User user);
        public void onCancel();
    }

    public interface GetTripListener{
        void onComplete(Trip trip);
        public void onCancel();
    }

    public interface GetTripLevelListener{
        void onComplete(TripLevel tripLevel);
        public void onCancel();
    }

    public interface GetTripTypeListener{
        void onComplete(TripType tripLevel);
        public void onCancel();
    }

    public interface GetAreaListener{
        void onComplete(Area area);
        public void onCancel();
    }

    public interface GetImageListener{
        public void onSuccess(Bitmap image);
        public void onFail();
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

    public static int getCurrentKeyTripLevel() {
        return currentKeyTripLevel;
    }

    public static void setCurrentKeyTripLevel(int currentKeyTripLevel) {
        Model.currentKeyTripLevel = currentKeyTripLevel;
    }

    public static void setCurrentKeyTrips(int currentKeyTrips) {
        Model.currentKeyTrips = currentKeyTrips;
    }

    public static int getCurrentKeyTripType() {
        return currentKeyTripType;
    }

    public static void setCurrentKeyTripType(int currentKeyTripType) {
        Model.currentKeyTripType = currentKeyTripType;
    }

    public static int getCurrentKeyArea() {
        return currentKeyArea;
    }

    public static void setCurrentKeyArea(int currentKeyArea) {
        Model.currentKeyArea = currentKeyArea;
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

    public void addTrip(final Trip trip, Bitmap tripImage, final SuccessListener listener){
        // Save the image on the SD-CARD
        if (tripImage != null){
            ImageLocal.saveLocalImage(tripImage, String.valueOf(trip.getId()));

            // Upload image to firebase storage
            ImageFirebase.saveRemoteImage(tripImage, String.valueOf(trip.getId()), new SaveImageListener() {
                @Override
                public void fail() {
                    listener.onResult(false);
                }

                @Override
                public void complete(String url) {
                    trip.setImageUrl(url);
                    remote.addTrip(trip, listener);

                    listener.onResult(true);
                }
            });
        }
        // Save without image
        else {
            remote.addTrip(trip, listener);
        }
        setCurrentKeyTrips(getCurrentKeyTrips() + 1);
    }

    public void addPlannedTrip(final PlannedTrip plannedTrip, final SuccessListener listener){


           //  plannedTrip.setTripId(Integer.parseInt(remote.getMaxIdPlannedTrip(listener)));
            // Save the dessert to firebase database
            remote.addPlannedTrip(plannedTrip, listener);

            // Update the key
            setCurrentKeyPlanned(getCurrentKeyPlanned() + 1);
    }

    public ArrayList<PlannedTrip> getTripsUserBelongs(final getTripsUserBelongsListener listener) {
        // Add the dessert to firebase databace
        return (remote.getTripsUserBelongs(listener));
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

    public void getTripById(String id, Model.GetTripListener listener){
        remote.getTripById(id, listener);
    }

    public void getTripImage(final Trip trip, int size, final Model.GetImageListener listener){
        Bitmap tripImage;

        // Get local image
        tripImage = ImageLocal.loadLocalImage(String.valueOf(trip.getId()), size);

        if (tripImage != null) {
            listener.onSuccess(tripImage);
        }
        // If there is not a local image
        else {
            ImageFirebase.loadRemoteImage(trip.getImageUrl(), new GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    // Save the image local
                    ImageLocal.saveLocalImage(image, String.valueOf(trip.getId()));
                    listener.onSuccess(image);
                }

                @Override
                public void onFail() {
                    listener.onFail();
                }
            });
        }
    }

    public void getAllTripAsynch(final GetAllTripsListener listener) {
        // Get last update date
        final double lastUpdateDate = TripSql.getLastUpdateDate(local.getReadableDB());

        // Get all trips records that where updated since last update date
        remote.getTripsFromDate(lastUpdateDate, new GetAllTripsListener() {
            @Override
            public void onComplete(List<Trip> tripsList, int currentMaxKey) {
                if (tripsList != null && tripsList.size() > 0) {

                    // Update the local db
                    double recentUpdate = lastUpdateDate;
                    for (Trip trip : tripsList) {
                        // If new trip
                        if (local.getTripById(trip.getId()) == null) {
                            TripSql.addTrip(local.getWritableDB(), trip);
                        }
                        //If this update
                        else {
                            // TODO
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

                // Return all trips from the updated local db
                List<Trip> result = TripSql.getAllTrips(local.getReadableDB());
                listener.onComplete(result, currentMaxKey);
            }

            @Override
            public void onCancel() {
                int a = 3;
            }
        });
    }

    public void addTripLevel(final TripLevel level, final SuccessListener listener){
        remote.addTripLevel(level, listener);
    }

    public TripLevel getTripLevelByCode(int code){
        return local.getTripLevelByCode(code);
    }

    public void getAllTripLevelsAsynch(final GetAllTripLevelsListener listener) {
        // Get last update date
        final double lastUpdateDate = TripLevelSql.getLastUpdateDate(local.getReadableDB());

        // Get all trips records that where updated since last update date
        remote.getTripLevelsFromDate(lastUpdateDate, new GetAllTripLevelsListener() {
            @Override
            public void onComplete(List<TripLevel> tripLevelsList, int currentMaxKey) {
                if (tripLevelsList != null && tripLevelsList.size() > 0) {

                    // Update the local db
                    double recentUpdate = lastUpdateDate;
                    for (TripLevel tripLevel : tripLevelsList) {
                        // If new trip
                        if (local.getTripLevelByCode(tripLevel.getCode()) == null) {
                            TripLevelSql.addTripLevel(local.getWritableDB(), tripLevel);
                        }
                        //If this update
                        else {
                            TripLevelSql.updateTripLevel(local.getWritableDB(), tripLevel);
                        }

                        if (tripLevel.getLastUpdated() > recentUpdate) {
                            recentUpdate = tripLevel.getLastUpdated();
                        }
                    }

                    // Update the last update date
                    TripLevelSql.setLastUpdateDate(local.getWritableDB(), recentUpdate);

                    // Update the current key
                    if (getCurrentKeyTripLevel() <= currentMaxKey) {
                        setCurrentKeyTripLevel(currentMaxKey + 1);
                    }
                }

                // Return all Desserts from the updated local db
                List<TripLevel> result = TripLevelSql.getAllTripLevels(local.getReadableDB());
                listener.onComplete(result, currentMaxKey);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void addTripType(final TripType type, final SuccessListener listener){
        remote.addTripType(type, listener);
    }

    public TripType getTripTypeByCode(int code){
        return local.getTripTypeByCode(code);
    }

    public void getAllTripTypesAsynch(final GetAllTripTypesListener listener) {
        // Get last update date
        final double lastUpdateDate = TripTypeSql.getLastUpdateDate(local.getReadableDB());

        // Get all trips records that where updated since last update date
        remote.getTripTypeFromDate(lastUpdateDate, new GetAllTripTypesListener() {
            @Override
            public void onComplete(List<TripType> tripTypesList, int currentMaxKey) {
                if (tripTypesList != null && tripTypesList.size() > 0) {

                    // Update the local db
                    double recentUpdate = lastUpdateDate;
                    for (TripType tripType : tripTypesList) {
                        // If new trip
                        if (local.getTripTypeByCode(tripType.getCode()) == null) {
                            TripTypeSql.addTripType(local.getWritableDB(), tripType);
                        }
                        //If this update
                        else {
                            TripTypeSql.updateTripType(local.getWritableDB(), tripType);
                        }

                        if (tripType.getLastUpdated() > recentUpdate) {
                            recentUpdate = tripType.getLastUpdated();
                        }
                    }

                    // Update the last update date
                    TripTypeSql.setLastUpdateDate(local.getWritableDB(), recentUpdate);

                    // Update the current key
                    if (getCurrentKeyTripType() <= currentMaxKey) {
                        setCurrentKeyTripType(currentMaxKey + 1);
                    }
                }

                // Return all Desserts from the updated local db
                List<TripType> result = TripTypeSql.getAllTripTypes(local.getReadableDB());
                listener.onComplete(result, currentMaxKey);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void addArea(final Area area, final SuccessListener listener){
        remote.addArea(area, listener);
    }

    public Area getAreaByCode(int code){
        return local.getAreaByCode(code);
    }

    public void getAllAreaAsynch(final GetAllAreaListener listener){
        // Get last update date
        final double lastUpdateDate = AreaSql.getLastUpdateDate(local.getReadableDB());

        // Get all trips records that where updated since last update date
        remote.getAreaFromDate(lastUpdateDate, new GetAllAreaListener() {
            @Override
            public void onComplete(List<Area> areaList, int currentMaxKey) {
                if (areaList != null && areaList.size() > 0) {

                    // Update the local db
                    double recentUpdate = lastUpdateDate;
                    for (Area area : areaList) {
                        // If new trip
                        if (local.getAreaByCode(area.getCode()) == null) {
                            AreaSql.addArea(local.getWritableDB(), area);
                        }
                        //If this update
                        else {
                            AreaSql.updateArea(local.getWritableDB(), area);
                        }

                        if (area.getLastUpdated() > recentUpdate) {
                            recentUpdate = area.getLastUpdated();
                        }
                    }

                    // Update the last update date
                    AreaSql.setLastUpdateDate(local.getWritableDB(), recentUpdate);

                    // Update the current key
                    if (getCurrentKeyArea() <= currentMaxKey) {
                        setCurrentKeyArea(currentMaxKey + 1);
                    }
                }

                // Return all Desserts from the updated local db
                List<Area> result = AreaSql.getAllAreas(local.getReadableDB());
                listener.onComplete(result, currentMaxKey);
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
