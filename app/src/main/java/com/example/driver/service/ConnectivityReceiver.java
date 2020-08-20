

package com.example.driver.service;
import android.content.BroadcastReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class ConnectivityReceiver extends BroadcastReceiver {

  public static ConnectivityReceiverListner connectivityReceiverListner;

  public ConnectivityReceiver() {
    super();
  }



  @Override
  public void onReceive(Context context, Intent intent) {

    ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    boolean isConnected= activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if(connectivityReceiverListner != null){
      connectivityReceiverListner.onNetworkConnectionChanged(isConnected);
    }

  }

  public static boolean isConnected(){
    ConnectivityManager cm =(ConnectivityManager) AppController.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork= cm.getActiveNetworkInfo();

    return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
  }


  public interface ConnectivityReceiverListner{
    void onNetworkConnectionChanged(boolean isConnected);
  }
}
