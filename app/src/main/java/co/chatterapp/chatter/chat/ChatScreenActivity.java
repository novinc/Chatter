package co.chatterapp.chatter.chat;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import co.chatterapp.chatter.R;

/**
 * Created by PutthidaSR on 10/18/15.
 */
public class ChatScreenActivity extends ListActivity{

    private Firebase mFirebaseRef;
    FirebaseListAdapter<User> mListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase("https://thechatterapp.firebaseio.com/");

        final EditText textEdit = (EditText) this.findViewById(R.id.message_input);
        Button sendButton = (Button) this.findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textEdit.getText().toString();
                Map<String,Object> values = new HashMap<>();
                values.put("name", "Android User");
                values.put("text", text);
                mFirebaseRef.push().setValue(values);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListAdapter.cleanup();
    }



}
