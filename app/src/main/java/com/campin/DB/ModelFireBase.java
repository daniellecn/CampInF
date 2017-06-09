package com.campin.DB;

import com.campin.Utils.PlannedTrip;
import com.campin.Utils.User;

/**
 * Created by noam on 03/06/2017.
 */

public class ModelFireBase {

    public ModelFireBase() {
    }

    public void userLogIn(User user, Model.LogInListener listener) {
        UserFireBase.userLogIn(user, listener);
    }

    public void userSignUp(final User user, final Model.SuccessListener listener) {

    }

    public void addPlannedTrip(PlannedTrip trip, Model.SuccessListener listener) {
        // Add the dessert to firebase databace
        PlannedTripFireBase.addPlannedTrip(trip, listener);
    }

    public void isUserNameExist(String userName, Model.SuccessListener listener) {
    }
}
