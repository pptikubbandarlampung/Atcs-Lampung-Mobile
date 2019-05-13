package app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.presenter.ILoginPresenter;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.presenter.LoginPresenter;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.view.ILoginView;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.registrasi.RegistrasiActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.progress_login)
    ProgressBar progressBar;

    @BindView(R.id.mail)
    EditText edEmail;
    @BindView(R.id.password)
    EditText edPass;

    ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.setProgressBarVisiblity(View.GONE);
    }

    @OnClick(R.id.signup)
    void registrasi(){
        Intent a = new Intent(LoginActivity.this, RegistrasiActivity.class);
        startActivity(a);
        finish();
    }

    @OnClick(R.id.signin)
    void login(){
        String e = edEmail.getText().toString();
        String p = edPass.getText().toString();
        if (e.isEmpty()){
            Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
        }else if(p.isEmpty()){
            Toast.makeText(getApplicationContext(), "Password tidak boleh kosong", Toast.LENGTH_LONG).show();
        }else if(e.isEmpty() && p.isEmpty()){
            Toast.makeText(getApplicationContext(), "Lengkapi data !", Toast.LENGTH_LONG).show();
        }else {
            loginPresenter.setProgressBarVisiblity(View.VISIBLE);
            loginPresenter.doLogin(edEmail.getText().toString(),
                    edPass.getText().toString());
        }
    }

    @Override
    public void onClearText() {

    }

    @Override
    public void onLoginResult(Boolean result, String msg) {
        loginPresenter.setProgressBarVisiblity(View.GONE);
        String[] spliData = msg.split("/");
        if (result){
            try {
                JSONArray jsonArray  = new JSONArray(spliData[0]);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String email        = jsonObject.getString("email");
                String userName     = jsonObject.getString("username");
                String namaLengkap  = jsonObject.getString("namalengkap");
                Log.v("emails", email +" " +userName +" "+ namaLengkap);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }
}