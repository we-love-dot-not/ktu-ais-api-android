package welovedotnot.lt.ktu_ais_api;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import welovedotnot.lt.ktu_ais_api.models.LoginModel;
import welovedotnot.lt.ktu_ais_api.models.MarkModel;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KtuAisClientUnitTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    @Test
    public void loginTest() throws Exception {
        LoginModel loginModel = KtuApiClient.INSTANCE.login(USERNAME, PASSWORD);
        Assert.assertNotNull(loginModel.getStudCookie());
        Assert.assertNotNull(loginModel.getStudentId());
        Assert.assertNotNull(loginModel.getStudentName());
        Assert.assertNotNull(loginModel.getStudentSemesters());
    }

    @Test
    public void getGradesTest() throws Exception {
        LoginModel loginModel = KtuApiClient.INSTANCE.login(USERNAME, PASSWORD);
        List<MarkModel> grades = KtuApiClient.INSTANCE.getGrades(
                loginModel.getStudCookie(),
                loginModel.getStudentSemesters().get(0));
        Assert.assertNotEquals(0, grades.size());
    }

}