package master.pam.world.servlet.impl.importer;

import com.dropbox.core.DbxException;
import com.master.pam.dropbox.api.IDropboxSource;
import master.pam.crosscutting.gson.GsonHelper;
import master.pam.crosscutting.spring.SpringContext;
import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.base.AbstractServerRequestServlet;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DropboxImportServlet", urlPatterns = "/DropboxImportServlet")
public class DropboxImportServlet extends AbstractServerRequestServlet {

    private static final long serialVersionUID = -7904700628451273567L;

    private IDropboxSource dropboxAPI = SpringContext.getBean(IDropboxSource.class);

    @Override
    protected ServerActionsEnum getServerAction() {
        return ServerActionsEnum.ADD_MARKERS;
    }

    @Override
    public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
        doPost(aRequest, aResponse);
    }

    @Override
    protected void buildServerRequest(IServerRequest aServerRequest) {
        String code = getHttpParam(RequestConstants.CODE);
        String[] files = GsonHelper.fromGson(getHttpParam(RequestConstants.FILES), String[].class);

        List<String> paths = new ArrayList<String>();

        for (String file : files) {
            String[] splited = StringUtils.split(file, "/");
            StringBuilder pathBuilder = new StringBuilder();
            for (int i = 5; i < splited.length; i++)
                pathBuilder.append("/").append(splited[i]);
            paths.add(pathBuilder.toString());
        }

        long userId = Long.parseLong(getHttpParam(RequestConstants.USER_ID));

        try {
            aServerRequest.addField(RequestConstants.DTO_LIST, dropboxAPI.getMarkers(code, paths, userId));
        } catch (DbxException e) {
            // TODO
            e.printStackTrace();
        }
    }
}
