package ch.ethz.inf.vs.a1.rubfisch.p2pchat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    //boolean myMessage = true;
    private List<ChatMessage> Messages;
    private ArrayAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Setup the Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //TODO: Initialize communication and set title to chat partner's name
        getSupportActionBar().setTitle("Robert Test");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff6e00")));

        //Set Button listener
        ImageButton send = (ImageButton) findViewById(R.id.send);
        send.setOnClickListener(this);

        Messages = new ArrayList<>();
        listView = (ListView) findViewById(R.id.chat);

        //set ListView adapter first
        adapter = new ChatMessageAdapter(this, R.layout.foreign_bubble, Messages);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.send) {
            //get the text
            EditText editText = (EditText) findViewById(R.id.editText);
            String text = editText.getText().toString();

            //get current time ( I used the deprecated Class Time to ensure backwards compatability)
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            String time = today.hour + ":" + today.minute;

            //add message to the list
            ChatMessage ChatMessage = new ChatMessage(text.toString(), true, "", time);
            Messages.add(ChatMessage);
            adapter.notifyDataSetChanged();
            editText.setText("");
            //TODO: Send your message "text" with your Name and a timestamp to the chat partners

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_people:
                // TODO: Add action of adding people to the chat: call InviteUserActvity;
                //   Also change the title of the activity to include the new ChatPartner: getSupportActionBar().setTitle("");                                                                   */
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds our add_people button to the action bar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /* TODO: When a message is received, the following has to be done:
     * 1. Retreive the following values from the message: the "text", the "sender", the "time_stamp"
     * 2. Create a new ChatMessage msg = new ChatMessage(text.toString(), false, sender, time_stamp);
     * 3. Add this message to our message list: Messages.add(msg);
     * 4. Notify the adapter, so that it can draw the new message on the listview: adapter.notifyDataSetChanged();
    */
}
