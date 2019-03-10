package com.pravrajya.diamond.views.users.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pravrajya.diamond.R;
import com.pravrajya.diamond.databinding.ActivityProfileBinding;
import com.pravrajya.diamond.views.BaseActivity;
import com.pravrajya.diamond.views.users.login.UserProfile;
import com.pravrajya.diamond.views.users.main.views.MainActivity;

import java.util.Objects;

import androidx.databinding.DataBindingUtil;

import static com.pravrajya.diamond.utils.Constants.USER_PROFILE;

public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        binding.etFullName.clearFocus();
        buttonClickHandler();
    }



    private void buttonClickHandler() {

        binding.btnLogin.setOnClickListener(v->{
            finish();
        });

        binding.btnRegistration.setOnClickListener(v->{
            String name = Objects.requireNonNull(binding.etFullName.getText()).toString();
            String emailId = Objects.requireNonNull(binding.etEmail.getText()).toString();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            String confirm_password = Objects.requireNonNull(binding.etConformPassword.getText()).toString();

            binding.inputName.setError(null);
            binding.inputEmail.setError(null);
            binding.inputPassword.setError(null);
            binding.inputConformPassword.setError(null);

            if (TextUtils.isEmpty(name)){
                showError("Please enter full valid name");
                binding.inputName.setError("Please enter full valid name");
            }else if (!isValidEmail(emailId)){
                showError("Please enter a valid e-mail address");
                binding.inputEmail.setError("Please enter a valid e-mail address");
            }else if(!isValidPassword(password)){
                showError("Password is not Valid");
                binding.inputPassword.setError("Password is not Valid");
            }else if(!password.equalsIgnoreCase(confirm_password)){
                showError("Password and Confirm Password does not Match");
                binding.inputConformPassword.setError("Password and Confirm Password does not Match");
            }else{
                firebaseCreateAccount(name,emailId, password);
            }
        });
    }



    private void firebaseCreateAccount(String name, String email, String password) {
        showProgressDialog("Registration in progress");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    hideProgressDialog();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        setUserProfile(user.getUid(), user.getEmail(), name, "");
                    } else {
                        showError(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    }
                });
    }


    private void setUserProfile(String uid, String email, String name, String userProfile) {
        Stash.put(USER_PROFILE,new UserProfile(uid, email, name, userProfile));
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}
