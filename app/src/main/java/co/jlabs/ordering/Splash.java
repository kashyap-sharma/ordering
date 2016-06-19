package co.jlabs.ordering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Splash extends Activity {
    Intent myIntent;
    public static int splash_time = 1000;
    Activity context;
    LinearLayout ll;


    private static String download_menu ="http://lannister-api.elasticbeanstalk.com/tyrion/menu?vendor_id=";
    private static String check_version ="http://lannister-api.elasticbeanstalk.com/tyrion/menu/check_version?vendor_id=1&version=";
    private CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        ll=(LinearLayout)findViewById(R.id.no_net);
        context = this;
        Log.e("hash fb",": "+printKeyHash(context));
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Log.i("Keys", "" + printHashKey(context));
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isInternetAvailable()) {

                    new InitSearch().execute();

                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);

                    snackbar.show();
                    //  ll.setVisibility(View.VISIBLE);


                    ha.postDelayed(this, 10000);
                }
            }
        }, 500);


    }

    public boolean isInternetAvailable() {

        return JSONfunctions.isNetworkAvailable(context);

    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    public static String printHashKey(Context ctx) {
        // Add code to print out the key hash
        try {
            PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "SHA-1 generation: the key count not be generated: NameNotFoundException thrown";
        } catch (NoSuchAlgorithmException e) {
            return "SHA-1 generation: the key count not be generated: NoSuchAlgorithmException thrown";
        }

        return "SHA-1 generation: epic failed";
    }

    private class InitSearch extends AsyncTask<String,Void,Void>
    {
        String versions;
        String email;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... args) {
                versions=Static_Catelog.getStringProperty(context,"version");
                email=Static_Catelog.getStringProperty(context,"email");
                if (versions==null)
                {
                    JSONObject json = JSONfunctions.getJSONfromURL(context, download_menu + 1);
                    try{
                        versions=json.getJSONObject("data").getString("version");
                        Static_Catelog.setStringProperty(context,"versions",versions);
                        Static_Catelog.setStringProperty(context,"jsonData",json.toString());
                    }
                  catch (JSONException e){
                      e.printStackTrace();
                  }
                }
                else
                {
                    JSONObject json1 = JSONfunctions.getJSONfromURL(context, check_version + versions);
                    boolean data=false;
                    try{
                       data= json1.getBoolean("data") ;
                    }
                    catch (Exception e){
                    }
                    if(!data){
                        JSONObject json = JSONfunctions.getJSONfromURL(context, download_menu + versions);
                        try{
                            versions=json.getJSONObject("data").getString("version");
                            Static_Catelog.setStringProperty(context,"versions",versions);
                            Static_Catelog.setStringProperty(context,"jsonData",json.toString());
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            return null;
        }


        @Override
        protected void onPostExecute(Void val) {
            super.onPostExecute(val);
                myIntent = new Intent(Splash.this, MainActivity.class);
                startActivity(myIntent);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        startActivity(myIntent);
                        context.finish();
                    }
                }, splash_time);

        }
    }
}

