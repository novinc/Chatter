package co.chatterapp.chatter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        SharedPreferences preferences = getSharedPreferences("authinfo", MODE_PRIVATE);
        if (!preferences.getBoolean("authenticated", false)) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Log.v("blah", "logged in with id: " + preferences.getString("uid", ""));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSharedPreferences("authinfo", MODE_PRIVATE).getBoolean("authenticated", false)) {
            ((TextView) findViewById(R.id.hello_world)).setText(((TextView) findViewById(R.id.hello_world)).getText()
                    + " " + getSharedPreferences("authinfo", MODE_PRIVATE).getString("uid", ""));
        }
        Intent intent = new Intent(this, BroadcastNearby.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v("blah", "service connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.v("blah", "disconnected");
            }
        }, Context.BIND_AUTO_CREATE);
        Intent intent1 = new Intent(this, FetchNearbyDevices.class);
        bindService(intent1, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_IMPORTANT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (!getSharedPreferences("authinfo", MODE_PRIVATE).getBoolean("authenticated", false)) {
            menu.findItem(R.id.action_logout).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SharedPreferences.Editor editor = getSharedPreferences("authinfo", MODE_PRIVATE).edit();
            editor.putBoolean("authenticated", false);
            editor.remove("uid");
            editor.apply();
            LoginManager.getInstance().logOut();
            item.setVisible(false);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
