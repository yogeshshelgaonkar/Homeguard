package android.example.homeguard;

import android.text.InputType;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pw;
    private Button login_btn;
    private Button registeratlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pw = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);
        registeratlogin = findViewById(R.id.registeratlogin);

        pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        registeratlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_str = email.getText().toString();
                String pw_str = pw.getText().toString();
                if (TextUtils.isEmpty(email_str) || TextUtils.isEmpty(pw_str)) {
                    Toast.makeText(LoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(email_str,pw_str);
                }

            }
        });
    }

    //Firebase Function
    private FirebaseAuth auth;
    private void loginUser(String email, String pw) {

        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,pw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));  //Intent from LoginActivity to Main
                finish();
            }
        });
        auth.signInWithEmailAndPassword(email,pw).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Failed to Login. Email or Password is incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}