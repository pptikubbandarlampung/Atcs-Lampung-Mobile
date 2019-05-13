package app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.login.view;

public interface ILoginView {
    public void onClearText();
    public void onLoginResult(Boolean result, String msg);
    public void onSetProgressBarVisibility(int visibility);
}
