package com.example.android.ubiquitouslabassistantsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Dheeraj_Kamath on 11/18/2017.
 */

public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Lights> lightsList;
    Boolean state1 = true;
    Boolean state2 = true;
    Boolean state3 = true;
    Boolean state4 = true;
    Boolean state5 = true;
    Boolean state6 = true;
    Boolean state7 = true;
    Boolean state8 = true;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, lightStatus;
        public ImageView thumbnail;
        public RelativeLayout relativeLayout;
        List<Lights> lightsDetails;
        Context context;

        public MyViewHolder(View view, Context context, List<Lights> lightsList) {
            super(view);
            this.lightsDetails = lightsList;
            this.context = context;
            view.setOnClickListener(this);
            title = view.findViewById(R.id.tvLights);
            lightStatus = view.findViewById(R.id.tvLightStatus);
            thumbnail = view.findViewById(R.id.ivLights);
            relativeLayout = view.findViewById(R.id.rlLights);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String parameter = " ";
            String ipaddress = LightsActivity.sp.getString("url", null);
            String portnumber = LightsActivity.sp.getString("port", null);
            Lights lightClicked = this.lightsDetails.get(position);
            String lightSelected = this.lightsDetails.get(position).getName();

            switch(position){
                case 0:
                    parameter = "LED1=";
                    state1 = !state1;
                    parameter = updateState(state1, parameter);
                    break;
                case 1:
                    parameter = "LED2=";
                    state2 = !state2;
                    parameter = updateState(state2, parameter);
                    break;
                case 2:
                    parameter = "LED3=";
                    state3 = !state3;
                    parameter = updateState(state3, parameter);
                    break;
                case 3:
                    parameter = "LED4=";
                    state4 = !state4;
                    parameter = updateState(state4, parameter);
                    break;
                case 4:
                    parameter = "LED5=";
                    state5 = !state5;
                    parameter = updateState(state5, parameter);
                    break;
                case 5:
                    parameter = "LED6=";
                    state6 = !state6;
                    parameter = updateState(state6, parameter);
                    break;
                case 6:
                    parameter = "LED7=";
                    state7 = !state7;
                    parameter = updateState(state7, parameter);
                    break;
                case 7:
                    parameter = "LED8=";
                    state8 = !state8;
                    parameter = updateState(state8, parameter);
                    break;
            }

            String[] status = parameter.split("=");
            lightStatus.setText("Device is: " + status[1]);

            if(status[1].equals("OFF")){
                relativeLayout.setBackgroundColor(Color.parseColor("#EFF0F1"));
                //thumbnail.setImageResource(R.drawable.light_off);
                Glide.with(mContext).load(R.drawable.light_off).into(thumbnail);
            }else{
                relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //thumbnail.setImageResource(R.drawable.light4);
                Glide.with(mContext).load(R.drawable.light4).into(thumbnail);
            }

            if (ipaddress.length() > 0 && portnumber.length() > 0) {
                new HttpRequestAsyncTask(view.getContext(), parameter, ipaddress).execute();
            }
        }
    }

    private String updateState(Boolean state, String parameter){
        if(state == true){
            parameter = parameter + "OFF";
        }else{
            parameter = parameter + "ON";
        }

        return parameter;
    }


    public LightsAdapter(Context mContext, List<Lights> lightsList) {
        this.mContext = mContext;
        this.lightsList = lightsList;
    }

    @Override
    public LightsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lights_single_item, parent, false);

        return new LightsAdapter.MyViewHolder(itemView, this.mContext, lightsList);
    }

    @Override
    public void onBindViewHolder(final LightsAdapter.MyViewHolder holder, int position) {
        Lights light = lightsList.get(position);
        holder.title.setText(light.getName());
        holder.lightStatus.setText("Device is: " + light.getDeviceStatus());

        Glide.with(mContext).load(light.getFlatIcon()).into(holder.thumbnail);

    }

    public int getItemCount() {
        return lightsList.size();
    }

    public static String sendRequest(String parameterValue, String ipAddress, String portNumber) {
        String serverResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // create an HTTP client
            // define the URL e.g. http://myIpaddress:myport/?pin=13 (to toggle pin 13 for example)
            URI website = new URI("http://" + ipAddress + "/" + parameterValue);
            Log.d("url", "http://" + ipAddress + "/" + parameterValue);
            HttpGet getRequest = new HttpGet(); // create an HTTP GET object
            getRequest.setURI(website); // set the URL of the GET request
            HttpResponse response = httpclient.execute(getRequest); // execute the request
            // get the ip address server's reply
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content
            ));
            serverResponse = in.readLine();
            // Close the connection
            content.close();
        } catch (ClientProtocolException e) {
            // HTTP error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            serverResponse = e.getMessage();
            e.printStackTrace();
        }
        // return the server's reply/response text
        return serverResponse;
    }

    public static class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {
        // declare variables needed
        private String requestReply,ipAddress, portNumber;
        private Context context;
        private AlertDialog alertDialog;
        private String parameterValue;

        /**
         * Description: The asyncTask class constructor. Assigns the values used in its other methods.
         * @param context the application context, needed to create the dialog
         * @param parameterValue the pin number to toggle
         * @param ipAddress the ip address to send the request to
         */
        public HttpRequestAsyncTask(Context context, String parameterValue, String ipAddress)
        {
            this.context = context;
            this.ipAddress = ipAddress;
            this.parameterValue = parameterValue;
        }

        /**
         * Name: doInBackground
         * Description: Sends the request to the ip address
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            requestReply = sendRequest(parameterValue,ipAddress,portNumber);
            return null;
        }

        /**
         * Name: onPostExecute
         * Description: This function is executed after the HTTP request returns from the ip address.
         * The function sets the dialog's message with the reply text from the server and display the dialog
         * if it's not displayed already (in case it was closed by accident);
         * @param aVoid void parameter
         */
        @Override
        protected void onPostExecute(Void aVoid) {
        }

        /**
         * Name: onPreExecute
         * Description: This function is executed before the HTTP request is sent to ip address.
         * The function will set the dialog's message and display the dialog.
         */
        @Override
        protected void onPreExecute() {
        }

    }


}
