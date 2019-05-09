package com.pravrajya.diamond.views.users.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


public class SignUpActivity extends BaseActivity {

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
                errorToast("Please enter full valid name");
                binding.inputName.setError("Please enter full valid name");
            }else if (!isValidEmail(emailId)){
                errorToast("Please enter a valid e-mail address");
                binding.inputEmail.setError("Please enter a valid e-mail address");
            }else if(!isValidPassword(password)){
                errorToast("Password is not Valid");
                binding.inputPassword.setError("Password is not Valid");
            }else if(!password.equalsIgnoreCase(confirm_password)){
                errorToast("Password and Confirm Password does not Match");
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
                        setUserProfile(user);
                    } else {
                        errorToast(Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    }
                });
    }


    private void setUserProfile(FirebaseUser user) {
        Stash.put(USER_PROFILE,new UserProfile(user.getUid(), user.getEmail(),
                user.getDisplayName(), user.getProviderData().toString(), user.getPhoneNumber(), user.getProviders().toString(),
                user.getPhotoUrl().toString()));
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}
