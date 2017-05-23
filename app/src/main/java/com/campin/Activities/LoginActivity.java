package com.campin.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.campin.R;
import com.campin.Utils.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private CallbackManager callbackManager;
    LoginButton _fcbkLoginButton;
    private static User usr;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        _fcbkLoginButton = (LoginButton) findViewById(R.id.fcbk_login);
        _fcbkLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends", "user_location, user_hometown"));

        login();
    }

    public void login() {
        Log.d(TAG, "Login");

        usr = usr.getInstance();

       final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if (isLoggedIn())
                        {
                            getFacebookDetails(AccessToken.getCurrentAccessToken());
                        }
                        else
                        {
                            progressDialog.cancel();
                            getLoginDetails(_fcbkLoginButton);

                        }
                        // onLoginSuccess();
                        // onLoginFailed();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        } else if (requestCode == 64206) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public static Bitmap getFacebookProfilePicture(String userID) {
        Bitmap bitmap = null;

        try {
            URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

            // bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    /*
     * Register a callback function with LoginButton to respond to the login result.
     */
    protected void getLoginDetails(LoginButton login_button) {

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                getFacebookDetails(login_result.getAccessToken());
            }

            @Override
            public void onCancel() {
                // code for cancellation

            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
            }
        });
    }

    protected void getFacebookDetails(AccessToken accessToken)
    {

        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        new GraphRequest(
                accessToken,
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        try {
                            JSONArray rawName = response.getJSONObject().getJSONArray("data");
                            usr.setFriends(rawName.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        // App code
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        String email = "";
                        String birthday = "";
                        String userId = "";
                        String fullName = "";
                        String urlCover = "";
                        String location = "";
                        // Application code
                        try {
                            email = object.getString("email");
                            birthday = object.getString("birthday");
                            userId = object.getString("id");
                            fullName = object.getString("name");
                             location = object.getJSONObject("hometown").getString("name");
                            JSONObject JOCover = object.optJSONObject("cover");

                            if (JOCover.has("source"))  {
                                urlCover = JOCover.getString("source");
                            } else {
                                urlCover = null;
                            }

                            usr.setUserId(userId);
                            usr.setBirthday(birthday);
                            usr.setEmail(email);
                            usr.setFullName(fullName);
                            usr.setLocation(location);
                            usr.setUrlCover(urlCover);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Bitmap bitmap = getFacebookProfilePicture(userId);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        usr.setByteArray(byteArray);

                        intent.putExtra("user", usr);
                        startActivity(intent);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,cover,hometown");
        request.setParameters(parameters);
        request.executeAsync();

    }
}