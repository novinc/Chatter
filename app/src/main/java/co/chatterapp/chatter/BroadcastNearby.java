package co.chatterapp.chatter;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class BroadcastNearby extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v("blah", "on bind");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("blah", "connected");
        byte[] array = {11, 22, 33};
        Message message = new Message(array);
        Nearby.Messages.publish(mGoogleApiClient, message)
                .setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("blah", "on connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v("blah", "connection failed");
    }

    @Override
    public void onResult(Status status) {
        Log.v("blah", " " + status.isSuccess());
    }
}
