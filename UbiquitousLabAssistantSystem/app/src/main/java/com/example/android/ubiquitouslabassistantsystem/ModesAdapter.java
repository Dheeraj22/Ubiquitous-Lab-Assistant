package com.example.android.ubiquitouslabassistantsystem;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
 * Created by Dheeraj_Kamath on 11/29/2017.
 */

public class ModesAdapter extends RecyclerView.Adapter<ModesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Modes> modesList;
    Boolean statelight = false;
    Boolean stateCurtain = false;
    Boolean stateProjector = false;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String ipProjector = ProjectorActivity.spProjector.getString("url_projector", "192.168.0.107");
    String ipCurtains = CurtainsActivity.spCurtain.getString("url_curtain", "192.168.0.107");
    String ipFans = FansActivity.spFan.getString("url_fan", "192.168.0.105");
    String ipLights = LightsActivity.sp.getString("url", "192.168.0.102");
    String portnumber = ProjectorActivity.spProjector.getString("port_projector", "80");

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout relativeLayout;
        List<Modes> modeDetails;
        Context context;

        public MyViewHolder(View view, Context context, List<Modes> modesList) {
            super(view);
            this.modeDetails = modesList;
            this.context = context;
            view.setOnClickListener(this);
            title = view.findViewById(R.id.tvModes);
            thumbnail = view.findViewById(R.id.ivModes);
            relativeLayout = view.findViewById(R.id.rlModes);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            switch(position){
                case 0:
                    statelight = !statelight;
                    stateCurtain = !stateCurtain;
                    stateProjector = !stateProjector;
                    movieProfile(view);
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }


//            if(position == 0){
//                relativeLayout.setBackgroundColor(Color.parseColor("#EFF0F1"));
//                //thumbnail.setImageResource(R.drawable.light_off);
//                Glide.with(mContext).load(R.drawable.open_selected).into(thumbnail);
//            }else if(position == 1){
//                relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                //thumbnail.setImageResource(R.drawable.light4);
//                Glide.with(mContext).load(R.drawable.close).into(thumbnail);
//            }else{
//                relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                //thumbnail.setImageResource(R.drawable.light4);
//                Glide.with(mContext).load(R.drawable.close).into(thumbnail);
//            }
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

    private void movieProfile(View view){
        lightsOff(view, statelight);
        Toast.makeText(mContext, "Lights Successful", Toast.LENGTH_SHORT).show();
        projectorDown(view, stateProjector);
        Toast.makeText(mContext, "Projector Successful", Toast.LENGTH_SHORT).show();
        curtainsClose(view, stateCurtain);
        Toast.makeText(mContext, "Curtains Successful", Toast.LENGTH_SHORT).show();
    }

    private void lightsOff(View view, Boolean stateLight){
        String parameter = "LED";

        DatabaseReference myRef = database.getReference("Lights");

        if(stateLight){
            for(int i = 1; i < 9; i++){
                if (ipLights.length() > 0 && portnumber.length() > 0) {
                    myRef.child("light"+ String.valueOf(i)).setValue("OFF");
                    new ModesAdapter.HttpRequestAsyncTask(view.getContext(), parameter + i + "=OFF", ipLights).execute();
                }
            }
        }else{
            for(int i = 1; i < 9; i++){
                if (ipLights.length() > 0 && portnumber.length() > 0) {
                    myRef.child("light"+ String.valueOf(i)).setValue("ON");
                    new ModesAdapter.HttpRequestAsyncTask(view.getContext(), parameter + i + "=ON", ipLights).execute();
                }
            }
        }

    }

    private void curtainsClose(View view, Boolean stateCurtain){
        String parameter = "CUR";

        DatabaseReference myRef = database.getReference("Curtains");

        if(stateCurtain){
            for(int i = 1; i < 3; i++){
                if (ipCurtains.length() > 0 && portnumber.length() > 0) {
                    myRef.child("curtain"+ String.valueOf(i)).setValue("ON");
                    new ModesAdapter.HttpRequestAsyncTask(view.getContext(), parameter + i + "=OFF", ipCurtains).execute();
                }
            }
        }else{
            for(int i = 1; i < 3; i++){
                if (ipCurtains.length() > 0 && portnumber.length() > 0) {
                    myRef.child("curtain"+ String.valueOf(i)).setValue("OFF");
                    new ModesAdapter.HttpRequestAsyncTask(view.getContext(), parameter + i + "=ON", ipCurtains).execute();
                }
            }
        }

    }

    private void projectorDown(View view, Boolean stateProjector){
        String parameter = "PRO";

        DatabaseReference myRef = database.getReference("Projectors");

        if(stateProjector){
            for(int i = 1; i < 3; i++){
                if (ipProjector.length() > 0 && portnumber.length() > 0) {
                    myRef.child("projector"+ String.valueOf(i)).setValue("ON");
                    new ModesAdapter.HttpRequestAsyncTask(view.getContext(), parameter + i + "=OFF", ipProjector).execute();
                }
            }
        }else{
            for(int i = 1; i < 3; i++){
                if (ipProjector.length() > 0 && portnumber.length() > 0) {
                    myRef.child("projector"+ String.valueOf(i)).setValue("OFF");
                    new ModesAdapter.HttpRequestAsyncTask(view.getContext(), parameter + i + "=ON", ipProjector).execute();
                }
            }
        }

    }


    public ModesAdapter(Context mContext, List<Modes> modesList){
        this.mContext = mContext;
        this.modesList = modesList;
    }

    @Override
    public ModesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modes_single_item, parent, false);

        return new ModesAdapter.MyViewHolder(itemView, this.mContext, modesList);
    }

    @Override
    public void onBindViewHolder(final ModesAdapter.MyViewHolder holder, int position) {
        Modes mode = modesList.get(position);
        holder.title.setText(mode.getName());
        Glide.with(mContext).load(mode.getFlatIcon()).into(holder.thumbnail);

    }

    public int getItemCount() {
        return modesList.size();
    }

    public static String sendRequest(String parameterValue, String ipAddress, String portNumber) {
        String serverResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // create an HTTP client
            // define the URL e.g. http://myIpaddress:myport/status
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
