package net.pranavprakash.peoplesoft;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Pranav
 */
@ManagedBean
@SessionScoped
public class AuthenticateUser implements Serializable {

    String userid;
    String pwd;

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String doLogin()
            throws Exception {
        String result = "pm:loginfailed?transition=flip";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpost = new HttpPost("http://www.hrcop.poornata.com:8994/psp/HRCOP/?cmd=login");

            List nvps = new ArrayList();
            nvps.add(new BasicNameValuePair("userid", this.userid));
            nvps.add(new BasicNameValuePair("pwd", this.pwd));

            httpost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);

            List cookies = httpclient.getCookieStore().getCookies();
            if ((!cookies.isEmpty()) && (response.getStatusLine().getStatusCode() == 302)) {
                for (int i = 0; i < cookies.size(); i++) {
                    Cookie pscookie = (Cookie) cookies.get(i);
                    if ((pscookie.getName().equals("PS_TOKEN")) && (pscookie.getDomain().equals("www.hrcop.poornata.com"))) {
                        result = "pm:main?transition=flip";
                        break;
                    }
                }
            }

        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }
}
