package com.example.labbayk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.labbayk.databinding.ActivitySigningBinding;

public class SigningActivity extends BaseActivity {
    ActivitySigningBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
    }

    private void setVariable() {

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigningActivity.this,LoginActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailEdit.getText().toString();
                String pass = binding.passEdit.getText().toString();

                if(pass.length()<6){
                    Toast.makeText(SigningActivity.this, "The password must have 6 characters at least.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SigningActivity.this, task ->  {
                        if(task.isSuccessful()) {
                            Log.i(tag, "Task done successfully.");
                            startActivity(new Intent(SigningActivity.this, RestaurantActivity.class));
                        }
                        else{
                            Log.i(tag, "Task failed."+task.getException());
                            Toast.makeText(SigningActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                });
            }
        });
    }
}