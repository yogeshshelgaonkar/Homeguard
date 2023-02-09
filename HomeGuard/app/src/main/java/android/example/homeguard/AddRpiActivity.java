package android.example.homeguard;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class AddRpiActivity extends AppCompatActivity {

    private Button addCamera;
    private EditText nameInput;
    private EditText ipInput;
    private String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrpi);
        addCamera = (Button) findViewById(R.id.addcamera);
        addCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                nameInput = findViewById(R.id.nameinput);
                ipInput = findViewById(R.id.ipinput);
                String camName = nameInput.getText().toString();
                String camIP = ipInput.getText().toString();

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String email = firebaseUser.getEmail();
                CurrentUser user = new CurrentUser(email);
                currentUser = user.getUserName();

                boolean isEmpty = false;

                if (nameInput.getText().length() == 0 || ipInput.getText().length() == 0){
                    Toast.makeText(AddRpiActivity.this, "Field cannot be empty!",Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras");
                    databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.child(camName).exists()){

                                boolean sameIP = false;
                                for(DataSnapshot c : snapshot.getChildren()){
                                    if(c.getValue().equals(camIP)){
                                        Toast.makeText(AddRpiActivity.this, "This IP address already exists!",Toast.LENGTH_SHORT).show();
                                        sameIP = true;
                                    }
                                }
                                if(!sameIP){
                                    IpHashMap ipmap = new IpHashMap(camName, camIP);
                                    HashMap<String,Object> map = ipmap.getMap();

                                    databaseRef.updateChildren(map);  // <- Firebase path cannot got symbols, so need to create a hash table
                                    Toast.makeText(AddRpiActivity.this, ("Camera " + camName + " is added to the list"),Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddRpiActivity.this,MainActivity.class));
                                }
                            }
                            else{
                                Toast.makeText(AddRpiActivity.this, camName + " already exists!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}