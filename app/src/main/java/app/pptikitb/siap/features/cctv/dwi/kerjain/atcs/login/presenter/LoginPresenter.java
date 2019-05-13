package app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.view.ILoginView;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.model.IUserLogin;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.model.ModelUserLogin;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.volley.AppController;
import app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.volley.Config_URL;


public class LoginPresenter implements ILoginPresenter{

    ILoginView iLoginView;
    IUserLogin user;
    Handler handler;

    RetryPolicy policy = new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public LoginPresenter(ILoginView iLoginView) {
       this.iLoginView = iLoginView;
       handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String email, String passwd) {
        checkLogin(email, passwd);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    private void checkLogin(final String email, final String password){

        String credentials = email + ":" + password;
        String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        user   = new ModelUserLogin(email, password);

        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config_URL.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg", "Login Response: " + response.toString());

                try {
                    final JSONObject jObj = new JSONObject(response);
                    final boolean status = jObj.getBoolean("success");


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(status == true){
                                String msg = null;
                                String api_token = null;
                                try {
                                    msg         = jObj.getString("message");
                                    api_token   = jObj.getString("token");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iLoginView.onLoginResult(status, msg + "/"+ api_token);
                            }else {
                                String msg = null;
                                try {
                                    msg = jObj.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iLoginView.onLoginResult(status, msg + "");
                            }
                        }
                    }, 1000);

                }catch (JSONException e){
                    //JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("msg", "Login Error : " + error.networkResponse.statusCode);
                error.printStackTrace();
                iLoginView.onLoginResult(false, "Maaf server tidak meresponse atau periksa koneksi internet anda");
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", basic);
                return params;
            }
        };
        strReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
