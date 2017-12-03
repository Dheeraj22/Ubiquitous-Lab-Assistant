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

public class ProjectorAdapter extends RecyclerView.Adapter<ProjectorAdapter.MyViewHolder> {

    private Context mContext;
    private List<Projector> projectorList;
    Boolean state1 = true;
    Boolean state2 = true;
    Boolean state3 = true;
    Boolean state4 = true;
    Boolean state5 = true;
    Boolean state6 = true;
    Boolean state7 = true;
    Boolean state8 = true;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout relativeLayout;
        List<Projector> projectorList;
        Context context;

        public MyViewHolder(View view, Context context, List<Projector> projectorList) {
            super(view);
            this.projectorList = projectorList;
            this.context = context;
            view.setOnClickListener(this);
            title = view.findViewById(R.id.tvProjector);
            thumbnail = view.findViewById(R.id.ivProjector);
            relativeLayout = view.findViewById(R.id.rlProjector);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String parameter = " ";
            String ipaddress = ProjectorActivity.spProjector.getString("url_projector", "192.168.0.107");
            String portnumber = ProjectorActivity.spProjector.getString("port_projector", "80");

            switch(position){
                case 0:
                    parameter = "PRO=";
                    state1 = !state1;
                    parameter = updateState(state1, parameter);
                    break;
                case 1:
                    parameter = "PRO=";
                    state2 = !state2;
                    parameter = updateState(state2, parameter);
                    break;
                case 2:
                    parameter = "PRO=";
                    state3 = !state3;
                    if(state3 == true){
                        parameter = parameter + "STOP";
                    }else{
                        parameter = parameter + "CONT";
                    }
                    break;
            }

            String[] status = parameter.split("=");
            //curtainStatus.setText("Device is: " + status[1]);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Projectors");
            myRef.child("projector"+ String.valueOf(position+1)).setValue(status[1]);

            if(position == 0){
                if(status[1].equals("OFF")){
                    relativeLayout.setBackgroundColor(Color.parseColor("#EFF0F1"));
                    //thumbnail.setImageResource(R.drawable.light_off);
                    Glide.with(mContext).load(R.drawable.arrow_up_selected).into(thumbnail);
                }else{
                    relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    //thumbnail.setImageResource(R.drawable.light4);
                    Glide.with(mContext).load(R.drawable.arrow_up).into(thumbnail);
                }
            }else if(position == 1){
                if(status[1].equals("OFF")){
                    relativeLayout.setBackgroundColor(Color.parseColor("#EFF0F1"));
                    //thumbnail.setImageResource(R.drawable.light_off);
                    Glide.with(mContext).load(R.drawable.arrow_down_selected).into(thumbnail);
                }else{
                    relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    //thumbnail.setImageResource(R.drawable.light4);
                    Glide.with(mContext).load(R.drawable.arrow_down).into(thumbnail);
                }
            }else{
                if(status[1].equals("OFF")){
                    relativeLayout.setBackgroundColor(Color.parseColor("#EFF0F1"));
                    //thumbnail.setImageResource(R.drawable.light_off);
                    Glide.with(mContext).load(R.drawable.stop1).into(thumbnail);
                }else{
                    relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    //thumbnail.setImageResource(R.drawable.light4);
                    Glide.with(mContext).load(R.drawable.stop_selected).into(thumbnail);
                }
            }


            if (ipaddress.length() > 0 && portnumber.length() > 0) {
                new ProjectorAdapter.HttpRequestAsyncTask(view.getContext(), parameter, ipaddress).execute();
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


    public ProjectorAdapter(Context mContext, List<Projector> projectorList) {
        this.mContext = mContext;
        this.projectorList = projectorList;
    }

    @Override
    public ProjectorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.projector_single_item, parent, false);

        return new ProjectorAdapter.MyViewHolder(itemView, this.mContext, projectorList);
    }

    @Override
    public void onBindViewHolder(final ProjectorAdapter.MyViewHolder holder, int position) {
        Projector projector = projectorList.get(position);
        holder.title.setText(projector.getName());
        //holder.curtainStatus.setText("Device is: " + curtain.getDeviceStatus());

        Glide.with(mContext).load(projector.getFlatIcon()).into(holder.thumbnail);

    }

    public int getItemCount() {
        return projectorList.size();
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
            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
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
