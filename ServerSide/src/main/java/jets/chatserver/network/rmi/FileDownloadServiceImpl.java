package jets.chatserver.network.rmi;

import commons.remotes.server.FileDownloadServiceInt;
import jets.chatserver.database.dao.FileDao;
import jets.chatserver.database.daoImpl.FileDaoImpl;
import jets.chatserver.gui.helpers.ModelsFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class FileDownloadServiceImpl extends UnicastRemoteObject implements FileDownloadServiceInt {

    public FileDownloadServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public byte[] downloadFile(Integer fileId) throws RemoteException {

        byte[] fileArr = null;
        try {

            FileDao fileDao = FileDaoImpl.getFileDaoInstance();
            fileArr = fileDao.getFile(fileId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  fileArr;
    }
}
