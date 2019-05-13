package app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.registrasi;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import app.pptikitb.siap.R;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;



public class RegistrasiActivity extends AppCompatActivity {

    @BindView(R.id.progress_login)
    ProgressBar progressBar;

    @BindView(R.id.username)
    EditText edUsername;
    @BindView(R.id.namaLengkap)
    EditText edNamaLengkap;
    @BindView(R.id.email)
    EditText edEmail;
    @BindView(R.id.password)
    EditText edPassword;
    @BindView(R.id.txtNamaLengkap)
    TextInputLayout txtNamaLengkap;
    @BindView(R.id.txtUsername)
    TextInputLayout txtUsername;
    @BindView(R.id.txtEmail)
    TextInputLayout txtEmail;
    @BindView(R.id.txtPassword)
    TextInputLayout txtPassword;
    @BindView(R.id.MySignUp)
    CoordinatorLayout mylayout;


    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        mSubscriptions = new CompositeSubscription();
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.signup)
    void regis(){

    }

    @OnClick(R.id.signin)
    void login(){
        Intent a = new Intent(RegistrasiActivity.this, LoginActivity.class);
        startActivity(a);
        finish();
    }
}