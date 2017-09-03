package welovedotnot.lt.ktu_ais_api;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import welovedotnot.lt.ktu_ais_api.models.LoginModel;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KtuAisClientUnitTest {

    @Test
    public void loginTest() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        KtuApiClient.INSTANCE.login("username", "password", new Function1<LoginModel, Unit>() {
            @Override
            public Unit invoke(LoginModel loginModel) {
                Assert.assertNotNull(loginModel.getStudCookie());
                Assert.assertNotNull(loginModel.getStudentId());
                Assert.assertNotNull(loginModel.getStudentName());
                Assert.assertNotNull(loginModel.getStudentSemesters());
                latch.countDown();
                return null;
            }
        });
        latch.await(10, TimeUnit.SECONDS);
    }
}