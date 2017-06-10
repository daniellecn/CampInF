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
        UserFireBase.userSignUp(user, new Model.SignUpListener() {
            @Override
            public void onComplete(boolean isExist) {
                if (isExist && !User.isSignUp) {
                    listener.onResult(false);
                }
                else if (isExist && User.isSignUp)
                {
                    UserFireBase.addUser(user);
                    listener.onResult(true);
                }
                else {
                    UserFireBase.addUser(user);
                    listener.onResult(true);
                }
            }
        });
    }

    public void addPlannedTrip(PlannedTrip trip, Model.SuccessListener listener) {
        // Add the dessert to firebase databace
        PlannedTripFireBase.addPlannedTrip(trip, listener);
    }

    public void isUserExist(String userId, Model.SuccessListener listener) {
        UserFireBase.isUserExist(userId, listener);
    }

    public User getUserById(String id, final Model.GetUserByIdListener listener) {
       User currUser  = UserFireBase.getUserById(id, listener);

        return  currUser;
    }
}
