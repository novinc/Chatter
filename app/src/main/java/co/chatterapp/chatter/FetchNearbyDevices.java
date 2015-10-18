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
import com.google.android.gms.nearby.messages.MessageListener;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class FetchNearbyDevices extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private MessageListener messageListener;
    private GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null) {
            Log.v("blah", "on bind");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Nearby.MESSAGES_API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();

        }
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("blah", "connected");
        messageListener = new MessageListener() {
            @Override
            public void onFound(final Message message) {
                Log.v("blah", "" + message.getContent().length);
            }
        };

        Nearby.Messages.subscribe(mGoogleApiClient, messageListener)
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
