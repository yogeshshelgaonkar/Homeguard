package android.example.homeguard;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listview;
    ImageButton addCamBtn;
    String name_IP;
    String currentUser;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Inflating Toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Toolbar functions
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(MainActivity.this, AddRpiActivity.class));
                finish();
                return true;
            case R.id.item2:
                Toast.makeText(MainActivity.this, "Call for Help selected", Toast.LENGTH_SHORT).show();

                String defaultCall = getString(R.string.default_call);
                Uri number = Uri.parse(defaultCall);
                Intent intent = new Intent(Intent.ACTION_DIAL, number);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "No App Available!", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.item3:
                doReset();
                return true;
            case R.id.item4:
                doLogOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        addCamBtn = findViewById(R.id.addCamButton);

        addCamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddRpiActivity.class));
                finish();
            }
        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();
        CurrentUser user = new CurrentUser(email);
        currentUser = user.getUserName();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.rpi_list,list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,"Clicked!" , Toast.LENGTH_SHORT).show(); //LINK HERE TO START WEBVIEW
//                startActivity(new Intent(MainActivity.this, WebActivity.class));
                name_IP = adapterView.getItemAtPosition(i).toString();  // <-- OMG I GOT IT NOW I KNOW THE IP AND NAME AS  A STRING
                Toast.makeText(MainActivity.this,name_IP , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("NameAndIP",name_IP);
                startActivity(intent);
            }
        });

        DatabaseReference userdata = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras");  // <- the name of branch to retrive data
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();     //<- clear list to prevent repeated entries when new cam ip is added

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Devices device = new Devices(snapshot.getKey(), snapshot.getValue().toString());   // <-- Created a helper class to get the device name and ip
                    String camName = device.getCamName();
                    String ip = device.getIp();
                    list.add(camName + " : " + ip);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ;
            }
        });

    }

    private void doReset(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras").removeValue();
        Toast.makeText(MainActivity.this, ("Camera list has been reset."),Toast.LENGTH_SHORT).show();
    }

    private void doLogOut(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Toast.makeText(MainActivity.this, currentUser + " logged out", Toast.LENGTH_SHORT).show();
        auth.signOut();
        startActivity(new Intent(MainActivity.this, StartActivity.class));
        finish();
    }
}