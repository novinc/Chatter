package co.chatterapp.chatter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by PutthidaSR on 10/18/15.
 */
public class ConversationActivity extends ListActivity {

    private Firebase mFirebaseRef;
    FirebaseListAdapter<User> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        mFirebaseRef = new Firebase("https://thechatterapp.firebaseio.com/");

        final EditText textEdit = (EditText) this.findViewById(R.id.text_edit);
        Button sendButton = (Button) this.findViewById(R.id.send_button);

        //send message to database
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textEdit.getText().toString();
                User message = new User("Android User", text);
                mFirebaseRef.push().setValue(message);
                textEdit.setText("");
            }
        });

        mListAdapter = new FirebaseListAdapter<User>(this, User.class,
                android.R.layout.two_line_list_item, mFirebaseRef) {
            @Override
            protected void populateView(View v, User model) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getName());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getMessage());
            }
        };
        setListAdapter(mListAdapter);

    }

    /**This method is to clean up our list adapter when the activity is destroyed.
     * This will close the connection to the Firebase server, when the activity is not showing. */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListAdapter.cleanup();
    }
}
