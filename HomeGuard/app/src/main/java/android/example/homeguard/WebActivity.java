package android.example.homeguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class WebActivity extends AppCompatActivity {

    WebView web;
    String URL;
    Button polis;
    Button back;
    Button remove;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        polis = findViewById(R.id.callpolisbtn);
        back = findViewById(R.id.action_back);
        remove = findViewById(R.id.action_removecamera);

        Intent intent = getIntent();
        String NameAndIP = intent.getStringExtra("NameAndIP");
        int index = NameAndIP.indexOf(":");
        String camName = NameAndIP.substring(0,index-1);

        urlGetter url = urlGetter.getInstance(NameAndIP);
        URL = url.getUrl();

        web =  findViewById(R.id.webView);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setSupportMultipleWindows(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl(URL);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WebActivity.this, "Back to list of cameras",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WebActivity.this,MainActivity.class));
            }
        });

        polis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WebActivity.this, "Call for Help selected", Toast.LENGTH_SHORT).show();

                String defaultCall = getString(R.string.default_call);
                Uri number = Uri.parse(defaultCall);
                Intent intent = new Intent(Intent.ACTION_DIAL, number);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(WebActivity.this, "No App Available!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Firebase function
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();
        CurrentUser user = new CurrentUser(email);
        currentUser = user.getUserName();

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Cameras").child(camName).removeValue();
                Toast.makeText(WebActivity.this,"Deleted "+ camName,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WebActivity.this,MainActivity.class));
            }
        });




    }
}