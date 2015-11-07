package dropbox;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;

import java.io.IOException;
import java.util.Locale;

public class DropboxAPITest {

    public static void main(String[] args) throws IOException, DbxException {

        // DbxAppInfo appInfo = new DbxAppInfo( DropboxConstants.APP_KEY,
        // DropboxConstants.APP_SECRET );

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        // DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect( config, appInfo
        // );
        //
        // // Have the user sign in and authorize your app.
        // String authorizeUrl = webAuth.start();
        // System.out.println( "1. Go to: " + authorizeUrl );
        // System.out.println( "2. Click \"Allow\" (you might have to log in first)"
        // );
        // System.out.println( "3. Copy the authorization code." );
        // String code = new BufferedReader( new InputStreamReader( System.in )
        // ).readLine().trim();
        //
        // // This will fail if the user enters an invalid authorization code.
        // DbxAuthFinish authFinish = webAuth.finish( code );
        // String accessToken = authFinish.accessToken;
        // System.out.println( "Access token: " + accessToken );

        DbxClient client = new DbxClient(config, "h0UzHPJUgK4AAAAAAAACJQSXgYB_eMRA_vPDZYsdEH5CoIcwelAf7Q7gnkdGMRar");

        System.out.println("Linked account: " + client.getAccountInfo().displayName);

    }
}
