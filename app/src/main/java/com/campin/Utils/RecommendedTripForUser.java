package com.campin.Utils;

import com.campin.DB.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Danielle Cohen on 01/08/2017.
 */

public class RecommendedTripForUser {
    private static int AREA_SCORE = 10;
    private static int SEASON_SCORE = 10;
    private static int TYPE_SCORE = 10;
    private static int LEVEL_SCORE = 10;
    private static double CAR_SCORE = 1.5;
    private static double COMMENT_SCORE = 2;

    public static void recommendedTripForUser(final User user, final Model.GetAllTripsListener listener){
        final List<Trip> recommendedTrips = new LinkedList<>();

        Model.instance().getAllTripAsynch(new Model.GetAllTripsListener() {
            @Override
            public void onComplete(List<Trip> tripsList, int currentMaxKey) {
                for (Trip trip : tripsList){
                    double tripScore = 1;

                    /*** Check Area ***/
                    if (user.getPreferedAreas().contains(trip.getArea())){
                        tripScore += AREA_SCORE;
                    }

                    /*** Check Seasons ***/
                    // If there is a match between current season and trip seasons
                    if (trip.getSeasons().contains(getSeasonForCurrentMonth(Calendar.getInstance().get(Calendar.MONTH) + 1))){
                        tripScore += SEASON_SCORE;
                    }
                    // If the is a match between previous or next month season and trip seasons
                    else if ((trip.getSeasons().contains(getSeasonForCurrentMonth(Calendar.getInstance().get(Calendar.MONTH) + 2))) ||
                            ((trip.getSeasons().contains(getSeasonForCurrentMonth(Calendar.getInstance().get(Calendar.MONTH) - 2))))){
                        tripScore += (SEASON_SCORE / 2);
                    }

                    /*** Check types ***/
                    for (int tripType : trip.getTypes()){
                        for (int userType : user.getPreferedTypes()){
                            if (tripType == userType){
                                tripScore += TYPE_SCORE;
                            }
                        }
                    }

                     /*** Check level ***/
                    // If there is a match between uses's level and level of trip
                    if (trip.getLevel() == user.getLevel()){
                        tripScore += LEVEL_SCORE;
                    }
                    // If the user's level is higher than the level of the trip
                    else if (trip.getLevel() < user.getLevel()){
                        tripScore += (LEVEL_SCORE / 2);
                    }

                    /*** Check car ***/
                    // If a car is needed for a trip and the user does not have a car
                    if ((trip.isMustCar() == true) && (user.isCar() == false)){
                        tripScore /= CAR_SCORE;
                    }

                    /*** Check comments ***/
                    double avgCommentsScore = 0;
                    for (TripComments comment : trip.getComments()){
                        avgCommentsScore += comment.get_commentScore();
                    }
                    avgCommentsScore = avgCommentsScore / trip.getComments().size();
                    tripScore *= ( avgCommentsScore + 1);

                    trip.setScore(tripScore);
                    recommendedTrips.add(trip);
                }

                // Sort the recommended trips by score
                Collections.sort(recommendedTrips, new Comparator<Trip>() {
                    @Override
                    public int compare(Trip recommendedTrip1, Trip recommendedTrip2) {
                        return Double.compare(recommendedTrip2.getScore(), recommendedTrip1.getScore());
                    }
                });
//                Collections.reverse(recommendedTrips);
                listener.onComplete(recommendedTrips, 0);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public static void recommendedTripForUsers(final PlannedTrip trip, final Model.GetAllTripsListener listener)
    {
        final List<Trip> recommendedTrips = new LinkedList<>();

        final ArrayList<String> usersId = trip.getFriends();
        final ArrayList<User> allUsers = new ArrayList<User>();
        usersId.add(User.getInstance().getId());

        for (String id : usersId)
        {
            Model.instance().getUserById(id, new Model.GetUserListener() {
                @Override
                public void onComplete(User user) {

                    allUsers.add(user);

                    if (allUsers.size() == usersId.size()) {

                        Model.instance().getAllTripAsynch(new Model.GetAllTripsListener() {
                            @Override
                            public void onComplete(List<Trip> tripsList, int currentMaxKey) {
                                for (Trip trip : tripsList){
                                    double tripScore = 1;

                                    for (User curr : allUsers) {
                                        /*** Check Area ***/
                                        if (curr != null) {
                                            if (curr.getPreferedAreas().size() > 0) {
                                                if (curr.getPreferedAreas().contains(trip.getArea())) {
                                                    tripScore += AREA_SCORE;
                                                }
                                            }


                                            /*** Check types ***/
                                            for (int tripType : trip.getTypes()) {
                                                for (int userType : curr.getPreferedTypes()) {
                                                    if (tripType == userType) {
                                                        tripScore += TYPE_SCORE;
                                                    }
                                                }
                                            }

                                            /*** Check level ***/
                                            // If there is a match between uses's level and level of trip
                                            if (trip.getLevel() == curr.getLevel()) {
                                                tripScore += LEVEL_SCORE;
                                            }
                                            // If the user's level is higher than the level of the trip
                                            else if (trip.getLevel() < curr.getLevel()) {
                                                tripScore += (LEVEL_SCORE / 2);
                                            }

                                            /*** Check car ***/
                                            // If a car is needed for a trip and the user does not have a car
                                            if ((trip.isMustCar() == true) && (curr.isCar() == false)) {
                                                tripScore /= CAR_SCORE;
                                            }
                                        }
                                    }


                                    /*** Check Seasons ***/
                                    // If there is a match between current season and trip seasons
                                    if (trip.getSeasons().contains
                                            (getSeasonForCurrentMonth(Calendar.getInstance().get(Calendar.MONTH) + 1))) {
                                        tripScore += SEASON_SCORE;
                                    }
                                    // If the is a match between previous or next month season and trip seasons
                                    else if ((trip.getSeasons().contains
                                            (getSeasonForCurrentMonth(Calendar.getInstance().get(Calendar.MONTH) + 2))) ||
                                            ((trip.getSeasons().
                                                    contains(getSeasonForCurrentMonth
                                                            (Calendar.getInstance().get(Calendar.MONTH) - 2))))) {
                                        tripScore += (SEASON_SCORE / 2);
                                    }

                                    /*** Check comments ***/
                                    double avgCommentsScore = 0;
                                    for (TripComments comment : trip.getComments()){
                                        avgCommentsScore += comment.get_commentScore();
                                    }
                                    avgCommentsScore = avgCommentsScore / trip.getComments().size();
                                    tripScore *= ( avgCommentsScore + 1);

                                    trip.setScore(tripScore);
                                    recommendedTrips.add(trip);
                                }

                                // Sort the recommended trips by score
                                Collections.sort(recommendedTrips, new Comparator<Trip>() {
                                    @Override
                                    public int compare(Trip recommendedTrip1, Trip recommendedTrip2) {
                                        return Double.compare(recommendedTrip1.getScore(), recommendedTrip2.getScore());
                                    }
                                });

//                                Collections.reverse(recommendedTrips);
                                listener.onComplete(tripsList, 0);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }
                }

                @Override
                public void onCancel() {

                }
            });

        }
    }


    private static String getSeasonForCurrentMonth(int month){
        String season = "";

        if ((month >= 1 && month <= 3) || (month == 12)){
            season = "חורף";
        }
        else if (month >= 4 && month <= 5){
            season = "אביב";
        }
        else if (month >= 6 && month <= 8 ){
            season = "קיץ";
        }
        else if (month >= 9 && month <= 11){
            season = "סתיו";
        }

        return season;
    }
}
