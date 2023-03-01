package Models;

import org.json.JSONObject;

public class MyData {
    protected User Usr;
    protected boolean Registr;

    public User getUsr() {
        return Usr;
    }

    public void setUsr(User usr) {
        Usr = usr;
    }

    public boolean isRegistr() {
        return Registr;
    }

    public void setRegistr(boolean registr) {
        Registr = registr;
    }

    public MyData(){}

    public MyData(User us,boolean reg){
        this.Usr=us;
        this.Registr=reg;
    }
    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("Usr{");
        sb.append("User='").append(Usr).append('\'');
        sb.append(",isReg='").append(Registr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
