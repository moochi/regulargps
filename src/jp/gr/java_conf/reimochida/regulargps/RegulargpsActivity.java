package jp.gr.java_conf.reimochida.regulargps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

public class RegulargpsActivity extends Activity {
	private static final int REQUEST_OAUTH = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*
        File file = new File("twitter4j.properties");
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
            }
            prop.setProperty("oauth.consumerKey", TwitterData.T_CONSUMERKEY_VALUE);
            prop.setProperty("oauth.consumerSecret", TwitterData.T_CONSUMERSECRET_VALUE);
            os = new FileOutputStream("twitter4j.properties");
            prop.store(os, "twitter4j.properties");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignore) {
                }
            }
        }
        */
    }
    
    public void onClickButtonStartGps() {
		Intent intent = new Intent(getApplicationContext(), GpsService.class);
		startService(intent);
	}

    public void onClickButtonStopGps() {
		Intent intent = new Intent(getApplicationContext(), GpsService.class);
		stopService(intent);
	}

    public void onClickButtonTwitter(View view) {
    	Intent intent = new Intent(this, TwitterAuthActivity.class);
    	intent.putExtra(TwitterAuthActivity.CALLBACK, "http://example.com");
    	intent.putExtra(TwitterAuthActivity.CONSUMER_KEY, TwitterData.T_CONSUMERKEY_VALUE);
    	intent.putExtra(TwitterAuthActivity.CONSUMER_SECRET, TwitterData.T_CONSUMERSECRET_VALUE);
    	startActivityForResult(intent, REQUEST_OAUTH);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == REQUEST_OAUTH) {
        	long userId = data.getLongExtra(TwitterAuthActivity.USER_ID, 0);
        	String screenName = data.getStringExtra(TwitterAuthActivity.SCREEN_NAME);
        	String token = data.getStringExtra(TwitterAuthActivity.TOKEN);
        	String tokenSecret = data.getStringExtra(TwitterAuthActivity.TOKEN_SECRET);
        	/*
        	Log.d("screen_name", screenName);
        	Log.d("token",token);
        	Log.d("token_secret", tokenSecret);
        	*/
    		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    		// 保存
    		SharedPreferences.Editor editor = pref.edit();
    		editor.putString(TwitterData.T_TOKEN, token);
    		editor.putString(TwitterData.T_TOKENSECRET, tokenSecret);
    		editor.commit();
		}
    }
}