package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application
{



    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate()
    {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("18MLbrJBTdEl4eiCc8hD3c8Yv3LKQB3WnOocw2bA")
                .clientKey("ewJ60qDbh2r4xVw2POqgvXpGGLXFS8X6RpAjhPCv")
                .server("https://parseapi.back4app.com")
                .build()
        );


    }
}
