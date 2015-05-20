package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //get Textview
        final TextView channelList = (TextView) findViewById(R.id.channel_list);

        //get buttons
        Button subscribeButton = (Button) findViewById(R.id.subscribe_giants);
        Button unSubscribeButton = (Button) findViewById(R.id.unsubscribe_giants);
        Button pushButton = (Button) findViewById(R.id.push_giants);

        //Subscribe button sets Giants channel if not already set - also display text
        subscribeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!ifSubscribed()){
                    ParsePush.subscribeInBackground("Giants", new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            channelList.setText("Giants");
                        }
                    });

                }
            }
        });

        //Unsubscribe button removes Giants channel if already set
        unSubscribeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(ifSubscribed()){
                    ParsePush.unsubscribeInBackground("Giants", new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            channelList.setText("No subscriptions");
                        }
                    });
                }
            }
        });

        //push button sends a push to the giants channel.
        // You can verify this on the parse console and your phone, if subscribed.
        pushButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                ParsePush push = new ParsePush();
                push.setChannel("Giants");
                push.setMessage("The Giants just scored! It's now 2-2 against the Mets.");
                push.sendInBackground();
            }
        });
    }

    public boolean ifSubscribed(){
        //check if device is already subscribed to Giants channel
        if(ParseInstallation.getCurrentInstallation().getList("channels").contains("Giants")) {
            return true;
        }
        else{
            return false;
        }
    }
}

