package ch.ethz.inf.vs.a1.rubfisch.p2pchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.vs.a1.rubfisch.p2pchat.dummy.DummyContent;

public class BroadcastReceiverActivity extends AppCompatActivity implements PeerListListener, PeerFragment.OnListFragmentInteractionListener{
    WifiP2pManager mManager;
    Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    private List peers = new ArrayList();
    private ListView mListView;
    private ArrayAdapter<String> WifiP2parrayAdapter;

    private PeerListListener peerListListener = new PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            // Out with the old, in with the new.
            peers.clear();
            peers.addAll(peerList.getDeviceList());
            //TODO: Prompt PeerFragment (ListFragment)
            WifiP2parrayAdapter.clear();
            for (WifiP2pDevice peer : peerList.getDeviceList()) {


                WifiP2parrayAdapter.add(peer.deviceName); //+ "\n" + peer.deviceAddress

                // set textbox search_result.setText(peer.deviceName);


            }
            // If an AdapterView is backed by this data, notify it
            // of the change.  For instance, if you have a ListView of available
            // peers, trigger an update.
            //((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
            if (peers.size() == 0) {
                //no devices found
                return;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        // get name entered by user in MainActivity
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("nameText");
        Log.d("name", name);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WifiBroadcastReceiver(mManager, mChannel, this);  //Setting up Wifi Receiver
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mListView = (ListView) findViewById(R.id.ListView);
        WifiP2parrayAdapter = new ArrayAdapter<String>(this, R.layout.fragment_peer,);
        mListView.setAdapter(WifiP2parrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Connect to selected item
                //Log.d("############","Items " +  MoreItems[arg2] );
            }

        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peerList) {
        List<WifiP2pDevice> devices = (new ArrayList<>());
        devices.addAll(peerList.getDeviceList());

        //do something with the device list
    }





    public void onRefresh(View view) {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //will not provide info about who it discovered
            }

            @Override
            public void onFailure(int reasonCode) {

            }
        });
    }




    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
