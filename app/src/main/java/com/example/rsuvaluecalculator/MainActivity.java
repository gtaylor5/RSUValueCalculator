package com.example.rsuvaluecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

  TextView valueTextView;
  EditText numSharesEditText;
  RequestQueue queue;
  StringRequest apiRequest;
  Thread pollingThread;

  private void pollStockPrice() {
    pollingThread = new Thread(new Runnable() {
      @Override
      public void run(){
        // while the thread isn't interrupted we want to poll for the stock price.
        while(pollingThread.isAlive()){
          queue.add(apiRequest); // add new request to queue.
          try {
            Thread.sleep(10000); // sleep (do nothing) for 10 seconds.
          } catch (InterruptedException e) {
            Log.d("Thread", "Error sleeping thread");
          }
        }
      }
    });
    pollingThread.start();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    valueTextView = findViewById(R.id.accountValue); // check out res/layout/activity_main.xml
    numSharesEditText = findViewById(R.id.numShares);
    queue = Volley.newRequestQueue(this);


    // What should the URL be?

    apiRequest = new StringRequest(Request.Method.GET, "INSERT API URL HERE", new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        // Handle API Response HERE.
        // How do you parse a JSON response?
        // Once the response is shown, how do we display the data we need in the valueTextView?
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        // Handle Errors Here.
        // What sort of information do we want to show to the user if the request fails?
        // Do we want to show a toast? or do we want to set the valueTextView to the error?
      }
    });
    pollStockPrice();
  }


  // we want to stop the polling thread if it's still running.
  @Override
  protected void onStop() {
    super.onStop();
    if(pollingThread != null)
      pollingThread.interrupt();
  }
}
