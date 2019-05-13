package app.pptikitb.siap.features.cctv.dwi.kerjain.atcs.model;

public class ModelUserLogin implements IUserLogin {
    String  email, password;

    public ModelUserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
