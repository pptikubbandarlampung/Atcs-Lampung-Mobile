package app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.presenter;

public interface ILoginPresenter {

    void clear();
    void doLogin(String email, String passwd);
    void setProgressBarVisiblity(int visiblity);
}
