package com.example.giriuygulamasi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

public class MainActivity extends AppCompatActivity {
    RelativeLayout giris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        giris = findViewById(R.id.huawei_giris);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huawei_giris();
            }
        });



    }
    public void huawei_giris()
    {
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setIdToken().createParams();
        AccountAuthService service = AccountAuthManager.getService(MainActivity.this, authParams);
        startActivityForResult(service.getSignInIntent(), 8888);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Process the authorization result to obtain an ID token from AuthAccount.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8888) {
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                // The sign-in is successful, and the user's ID information and ID token are obtained.
                AuthAccount authAccount = authAccountTask.getResult();
                Toast.makeText(this, ""+authAccount.getEmail()+authAccount.getDisplayName()+authAccount.getIdToken(), Toast.LENGTH_LONG).show();
                Log.i("TAG", "idToken:" + authAccount.getIdToken());
            } else {
                // The sign-in failed. No processing is required. Logs are recorded for fault locating.
                Log.e("TAG", "sign in failed : " +((ApiException)authAccountTask.getException()).getStatusCode());
            }
        }
    }
}