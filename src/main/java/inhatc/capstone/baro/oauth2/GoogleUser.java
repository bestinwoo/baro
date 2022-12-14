package inhatc.capstone.baro.oauth2;

import java.util.Map;
/**
 * <h1>GoogleUser</h1>
 * <p>구글 로그인 사용자의 계정 정보 클래스</p>
 */
public class GoogleUser extends OAuth2UserInfo{

    protected GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getOAuth2Id() {
        return (String) super.attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) super.attributes.get("email");
    }

    @Override
    public OAuth2Provider getOAuth2Provider() {
        return OAuth2Provider.GOOGLE;
    }
}
