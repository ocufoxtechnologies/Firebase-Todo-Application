package com.ocufoxtech.firebasetodoruia.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    List<AuthUI.IdpConfig> providers = Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build());

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (auth.getCurrentUser() == null){
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            startActivityForResult(signInIntent,9999);
        }else{
            launchActivity(auth.getCurrentUser());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9999){
            if (resultCode == RESULT_OK){
                launchActivity(auth.getCurrentUser());
            }else{
                Log.e("LoginActivity", "onActivityResult: ",IdpResponse.fromResultIntent(data).getError());
                Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void launchActivity(FirebaseUser currentUser) {
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("name",currentUser.getDisplayName());
        i.putExtra("email",currentUser.getEmail());
        startActivity(i);
        finish();
    }
}
